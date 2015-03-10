var logicServices = angular.module('logicServices', ['ngResource']);

logicServices.factory('sequentSvc', ["$http","$q","$window",function ($http, $q, $window) {
	
	function isValid(sequent) {
		var deferred = $q.defer();
        $http.get("/blomqcon/logic-solver/api/isValid.php?sequent=" + encodeURIComponent(sequent).replace("&", "%26"))
            .then(function (result) {
            	var valid = result.data;
                deferred.resolve(valid.trim());
            }, function (error) {
                deferred.reject(error);
            });

        return deferred.promise;
	}
	
	function getTruthTable(sequent) {
		var deferred = $q.defer();
        $http.get("/blomqcon/logic-solver/api/getTruthTable.php?sequent=" + encodeURIComponent(sequent).replace("&", "%26"))
            .then(function (result) {
            	var table = result.data;
                deferred.resolve(table);
            }, function (error) {
                deferred.reject(error);
            });

        return deferred.promise;
	}
	
	function validateProof(sequent, proof) {
		var deferred = $q.defer();
		console.log("/blomqcon/logic-solver/api/validateProof.php?sequent=" + encodeURIComponent(sequent).replace("&", "%26") +
        	"&proof=" + encodeURIComponent(proof).replace("&", "%26"));
		console.log(sequent);
		console.log(proof);
        $http.get("/blomqcon/logic-solver/api/validateProof.php?sequent=" + encodeURIComponent(sequent).replace("&", "%26") +
        	"&proof=" + encodeURIComponent(proof).replace("&", "%26"))
            .then(function (result) {
            	var data = result.data;
                deferred.resolve(data);
            }, function (error) {
                deferred.reject(error);
            });
		//deferred.resolve("hello");

        return deferred.promise;
	}
	
	return {
        isValid: isValid,
        getTruthTable: getTruthTable,
        validateProof: validateProof
    };
}]);