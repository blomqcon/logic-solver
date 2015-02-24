var logicServices = angular.module('logicServices', ['ngResource']);

logicServices.factory('sequentSvc', ["$http","$q","$window",function ($http, $q, $window) {
	
	function isValid(sequent) {
		var deferred = $q.defer();
        $http.get("/blomqcon/logic-solver/api/isValid.php?sequent=" + sequent)//, { sequent: sequent })
            .then(function (result) {
            	var valid = result.data;
                deferred.resolve(valid.trim());
            }, function (error) {
                deferred.reject(error);
            });

        return deferred.promise;
		//return true;
	}
	
	return {
        isValid: isValid
    };
}]);