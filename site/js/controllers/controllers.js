var logicControllers = angular.module('logicControllers', []);

logicControllers.controller("HomeCtrl", ["$scope", "$mdDialog", "$location", "sequentSvc",function ($scope, $mdDialog, $location, sequentSvc) {
    $scope.sequent = "P, P→Q ⊢ Q";
    
	$scope.checkSequent = function (ev) {
		var sequent = $scope.sequent.replace(/ /g,'');
		
		sequentSvc.isValid(sequent)
        .then(function (isValid) {
    		showAlert(ev, isValid);
        }, function (error) {
            console.log(error);
        });

		function showAlert(ev, valid) {
			if(valid == "true") {
	            $mdDialog.show(
	          	      $mdDialog.alert()
	          	        .title('Sequent valid')
	          	        .content('The Sequent you entered is valid! :)')
	          	        .ariaLabel('Password notification')
	          	        .ok('Got it!')
	          	        .targetEvent(ev)
	          	    )
	    	} else {
	    		$mdDialog.show(
	            	      $mdDialog.alert()
	            	        .title('Sequent Invalid')
	            	        .content('The Sequent you entered is invalid! :(')
	            	        .ariaLabel('Password notification')
	            	        .ok('Got it!')
	            	        .targetEvent(ev)
	            	    )
	    	}
	    }
	};
    
}]);

logicControllers.controller("TestCtrl", ["$scope", "$location", "$window", "testSvc",function ($scope, $location, $window, testSvc) {
	
}]);

