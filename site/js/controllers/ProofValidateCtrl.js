var logicControllers = angular.module('ProofValidateCtrl', []);

logicControllers.controller("ProofValidateCtrl", ["$scope", "$location", "$window", "sequentSvc", function ($scope, $location, $window, sequentSvc) {
	$scope.loading = false;
	$scope.message = "";
	$scope.data = [{assumptions: "1", formula: "Pv(P&Q)", justificationLines: "", justificationRule: "A"},
                {assumptions: "2", formula: "P", justificationLines: "", justificationRule: "A"},
                {assumptions: "3", formula: "P&Q", justificationLines: "", justificationRule: "A"},
                {assumptions: "3", formula: "P", justificationLines: "3", justificationRule: "&E"},
                {assumptions: "1", formula: "P", justificationLines: "1, 2, 2, 3, 4", justificationRule: "vE"}];
	$scope.sequent = "Pv(P&Q) ⊢ P";
	updateHeight();
	
	$scope.logicSymbol = function (ev) {
    	$scope.sequent += ev.currentTarget.value;
    	document.getElementById("sequent").focus();
    };
	
	$scope.clearAll = function() {
		$scope.data =  [];
		$scope.sequent = "";
		updateHeight();
	}
	
	$scope.removeRow = function() {
	   var index = this.row.rowIndex;
	   $scope.data.splice(index, 1);
	   updateHeight();
	};
		
	$scope.addRow = function() {
		var row = {assumptions: $scope.newAssumptions, formula: $scope.newFormula, justificationLines: $scope.newJustificationLines, justificationRule: $scope.newJustificationRule}
		$scope.data.push(row);
		$scope.newAssumptions = "";
		$scope.newFormula = "";
		$scope.newJustificationLines = "";
		$scope.newJustificationRule = "";
		updateHeight();
	};
	
	$scope.updateJustLines = function() {
		if($scope.newJustificationRule == "A") {
			$scope.newJustificationLines = "";	
		}
	}

	$scope.validateProof = function (ev) {
		$scope.loading = true;
		$scope.message = "";
		var sequent = $scope.sequent;
		var proof = JSON.stringify($scope.data);
		
		sequentSvc.validateProof(sequent, proof)
        .then(function (proofResponse) {
    		showProofResponse(ev, proofResponse);
        }, function (error) {
            console.log(error);
            $scope.loading = false;
        });
	}
	
	function showProofResponse(ev, proofResponse) {
		$scope.loading = false;
		if(proofResponse.result == "Valid") {
			$scope.message = "The proof is valid";
		    $scope.messageColor = "green";
		} else if (proofResponse.result == "") {
			$scope.message = "There was an error validating the proof, likely a formula is not well formed";
			$scope.messageColor = "yellow";
		} else if (proofResponse.result != null) {
			$scope.message = "There is an error on line " + (parseInt(proofResponse.lineNumber) + 1) + " of the proof: " + proofResponse.result;
		    $scope.messageColor = "red";
		} else {
			$scope.message = "There was an error validating the proof";
			$scope.messageColor = "yellow";
		}
	}


	function updateHeight() {
		var proof = document.getElementById("proof");
		proof.style.height = "" + (60 + ($scope.data.length * 30)) + "px";
	}
	
    $scope.gridOptions = {
        data: 'data',
        enableRowSelection: false,
        enableCellSelection: true,
        enableCellEdit: true,
        enableColumnResize: true,
        enableSorting: false,
        columnDefs: [{field: 'assumptions', displayName: 'Assumptions'}, 
                     {field:'lineNumber', displayName:'Line Number', enableCellEdit: false, cellTemplate: "<div>{{row.rowIndex + 1}}</div>"},
                     {field:'formula', displayName:'Formula'},
                     {field:'justificationLines', displayName:'Justification'},
                     {field:'justificationRule', displayName:''},
                     {field:'delete', displayName:'', enableCellEdit: false, cellTemplate: "<md-button md-no-ink class='md-primary' ng-click='removeRow($event)'>x</md-button>"}]
    };
    
    $scope.rules = [{name: "A"}, {name: "MPP"}, {name:"MTT"}, {name:"DN"}, {name:"Df ↔"}, {name:"&I"}, {name:"&E"}, {name:"vI"}, {name:"vE"}, {name:"CP"}, {name:"RAA"}];
    
    $scope.loadExample = function(ev) {
		switch (ev.currentTarget.value) {
        case "0":
            $scope.sequent = "P→Q, -Q ⊢ -P";
            $scope.data = [{assumptions: "1", formula: "P→Q", justificationLines: "", justificationRule: "A"},
                           {assumptions: "2", formula: "-Q", justificationLines: "", justificationRule: "A"},
                           {assumptions: "1, 2", formula: "-P", justificationLines: "1, 2", justificationRule: "MTT"}];
            break;
        case "1":
            $scope.sequent = "Pv(P&Q) ⊢ P";
            $scope.data = [{assumptions: "1", formula: "Pv(P&Q)", justificationLines: "", justificationRule: "A"},
                           {assumptions: "2", formula: "P", justificationLines: "", justificationRule: "A"},
                           {assumptions: "3", formula: "P&Q", justificationLines: "", justificationRule: "A"},
                           {assumptions: "3", formula: "P", justificationLines: "3", justificationRule: "&E"},
                           {assumptions: "1", formula: "P", justificationLines: "1, 2, 2, 3, 4", justificationRule: "vE"}];
            break;
        case "2":
            $scope.sequent = "Q ⊢ P→Q";
            $scope.data = [{assumptions: "1", formula: "Q", justificationLines: "", justificationRule: "A"},
                           {assumptions: "2", formula: "P", justificationLines: "", justificationRule: "A"},
                           {assumptions: "1", formula: "P→Q", justificationLines: "2, 1", justificationRule: "CP"}];
            break;
        case "3":
            $scope.sequent = "P ⊢ P";
            $scope.data = [{assumptions: "1", formula: "P", justificationLines: "", justificationRule: "A"},
                           {assumptions: "2", formula: "-P", justificationLines: "", justificationRule: "A"},
                           {assumptions: "1, 2", formula: "P&-P", justificationLines: "1, 2", justificationRule: "&I"},
                           {assumptions: "1", formula: "--P", justificationLines: "2, 3", justificationRule: "RAA"},
                           {assumptions: "1", formula: "P", justificationLines: "4", justificationRule: "DN"}];
            break;
    	}
	}
}]);