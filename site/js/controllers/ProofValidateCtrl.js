var logicControllers = angular.module('ProofValidateCtrl', []);

logicControllers.controller("ProofValidateCtrl", ["$scope", "$location", "$window", "sequentSvc", function ($scope, $location, $window, sequentSvc) {
	$scope.loading = false;
	$scope.message = "";
	$scope.data = [{assumptions: "1", formula: "P → Q", justificationLines: "", justificationRule: "A"},
                {assumptions: "2", formula: "P", justificationLines: "", justificationRule: "A"},
                {assumptions: "1, 2", formula: "Q", justificationLines: "1, 2", justificationRule: "MPP"}];
	$scope.sequent = "P → Q, P ⊢ Q";
	updateHeight();
	
	$scope.removeRow = function() {
	   var index = this.row.rowIndex;
	   $scope.data.splice(index, 1);
	   updateHeight();
	};
		
	$scope.addRow = function() {
		var row = {assumptions: $scope.newAssumptions, formula: $scope.newFormula, justification: $scope.newJustification}
		$scope.data.push(row);
		$scope.newAssumptions = "";
		$scope.newFormula = "";
		$scope.newJustification = "";
		updateHeight();
	};

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
		} else if (proofResponse.result != null) {
			$scope.message = "There is an error on line " + (proofResponse.lineNumber + 1) + " of the proof: " + proofResponse.result;
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
        enableFocusedCellEdit: true,
        enableColumnResize: true,
        enableSorting: false,
        columnDefs: [{field: 'assumptions', displayName: 'Assumptions'}, 
                     {field:'lineNumber', displayName:'Line Number', enableCellEdit: false, cellTemplate: "<div>{{row.rowIndex + 1}}</div>"},
                     {field:'formula', displayName:'Formula'},
                     {field:'justificationLines', displayName:'Justification'},
                     {field:'justificationRule', displayName:''},
                     {field:'delete', displayName:'', enableCellEdit: false, cellTemplate: "<md-button md-no-ink class='md-primary' ng-click='removeRow($event)'>x</md-button>"}]
    };
}]);