var logicControllers = angular.module('TruthTableCtrl', []);

logicControllers.controller("TruthTableCtrl", ["$scope", "$mdDialog", "$location", "sequentSvc",function ($scope, $mdDialog, $location, sequentSvc) {
    $scope.loading = false;
	$scope.sequent = "P, P→Q ⊢ Q";
    
    $scope.logicSymbol = function (ev) {
    	$scope.sequent += ev.currentTarget.value;
    	document.getElementById("sequent").focus();
    };
    
	$scope.checkSequent = function (ev) {
		var sequent = $scope.sequent.replace(/ /g,'');
		$scope.message = "";
		$scope.tableHeaders = [];
		$scope.tableValues = [];
		$scope.loading = true;
		
		sequentSvc.isValid(sequent)
        .then(function (isValid) {
    		showAlert(ev, isValid);
        }, function (error) {
            console.log(error);
        });
		
		sequentSvc.getTruthTable(sequent)
        .then(function (table) {
    		showTable(table);
        }, function (error) {
        	$scope.loading = false;
            console.log(error);
        });
		
		function showAlert(ev, valid) {
			if(valid === "true") {
			    $scope.message = "The sequent is valid.";
			    $scope.messageColor = "green";
	    	} else if(valid === "false"){
	    		$scope.message = "The sequent is invalid.";
	    		$scope.messageColor = "red";
	    	} else {
	    		$scope.message = "The sequent is not well formed."
	    		$scope.messageColor = "yellow";
	    	}
	    }
		
		function showTable(table) {
			if(table) {
				var cols = [];
				for(var i = 0; i < table.headers.length; i++) {
					cols[i] = "";
				}
				for(var i = 0; i < table.mainConnectors.length; i++) {
					cols[table.mainConnectors[i]] = "background-color: #C5CAE9;";
				}
				$scope.loading = false;
				$scope.tableCols = cols;
				$scope.tableHeaders = table.headers;
				$scope.tableValues = table.values;
			}
		}
	};
	
	$scope.loadExample = function(ev) {
		switch (ev.currentTarget.value) {
        case "0":
            $scope.sequent = "P→Q, -Q ⊢ -P";
            break;
        case "1":
            $scope.sequent = "P→Q ⊢ -Q→-P";
            break;
        case "2":
            $scope.sequent = "-(PvQ) ⊢ -P&-Q";
            break;
        case "3":
            $scope.sequent = "-P, PvQ ⊢ Q";
            break;
    	}
	}
}]);