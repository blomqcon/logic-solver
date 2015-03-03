var logicServices = angular.module('logicServices', ['ngResource']);

logicServices.factory('sequentSvc', ["$http","$q","$window",function ($http, $q, $window) {
	
	function isValid(sequent) {
		var deferred = $q.defer();
        $http.get("/blomqcon/logic-solver/api/isValid.php?sequent=" + sequent)
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
        $http.get("/blomqcon/logic-solver/api/getTruthTable.php?sequent=" + sequent)
            .then(function (result) {
            	var table = result.data;
                deferred.resolve(table);
            }, function (error) {
                deferred.reject(error);
            });

        return deferred.promise;
	}
	
	return {
        isValid: isValid,
        getTruthTable: getTruthTable
    };
}]);