var logicControllers = angular.module('logicControllers', []);

logicControllers.controller("HomeCtrl", ["$scope", "$location", "$window", "testSvc",function ($scope, $location, $window, testSvc) {
	
}]);


logicControllers.controller("TruthTableCtrl", ["$scope", "$mdDialog", "$location", "sequentSvc",function ($scope, $mdDialog, $location, sequentSvc) {
    $scope.sequent = "P, P→Q ⊢ Q";
    $scope.truthTable = [];
    
	$scope.checkSequent = function (ev) {
		var sequent = $scope.sequent.replace(/ /g,'');
		
		sequentSvc.isValid(sequent)
        .then(function (isValid) {
    		showAlert(ev, isValid);
        }, function (error) {
            //console.log(error);
        });
		
		sequentSvc.getTruthTable(sequent)
        .then(function (table) {
    		showTable(table);
        }, function (error) {
            //console.log(error);
        });

		function showAlert(ev, valid) {
			if(valid === "true") {
			    $scope.message = "The sequent is valid.";
			    $scope.messageColor = "green";
	            $mdDialog.show(
	          	      $mdDialog.alert()
	          	        .title('Sequent valid')
	          	        .content('The Sequent you entered is valid!')
	          	        .ariaLabel('Password notification')
	          	        .ok('Got it!')
	          	        .targetEvent(ev)
	          	    )
	    	} else if(valid === "false"){
	    		$scope.message = "The sequent is invalid.";
	    		$scope.messageColor = "red";
	    		$mdDialog.show(
	            	      $mdDialog.alert()
	            	        .title('Sequent Invalid')
	            	        .content('The Sequent you entered is invalid!')
	            	        .ariaLabel('Password notification')
	            	        .ok('Got it!')
	            	        .targetEvent(ev)
	            	    )
	    	} else {
	    		$scope.message = "The sequent is not well formed."
	    		$scope.messageColor = "yellow";
	    		$scope.tableHeaders = "";
				$scope.tableValues = "";
	    		$mdDialog.show(
	            	      $mdDialog.alert()
	            	        .title('Sequent not well formed')
	            	        .content("Either the sequent or one of it's formulas is not well formed.")
	            	        .ariaLabel('Password notification')
	            	        .ok('Got it!')
	            	        .targetEvent(ev)
	            	    )
	    	}
	    }
		
		function showTable(table) {
			$scope.tableHeaders = table.headers;
			$scope.tableValues = table.values;
		}
	};
    
}]);

logicControllers.controller("ProofValidteCtrl", ["$scope", "$location", "$window", "testSvc",function ($scope, $location, $window, testSvc) {
	
}]);

logicControllers.controller("ProofSolveCtrl", ["$scope", "$location", "$window", "testSvc",function ($scope, $location, $window, testSvc) {
	
}]);
