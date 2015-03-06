var logicControllers = angular.module('ProofValidateCtrl', []);

logicControllers.controller("ProofValidateCtrl", ["$scope", "$location", "$window",function ($scope, $location, $window) {
	
	$scope.data = [{assumptions: "1", formula: "P > Q", justification: "A"},
                {assumptions: "2", formula: "P", justification: "A"},
                {assumptions: "1, 2", formula: "Q", justification: "1, 2 MPP"}];
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
                     {field:'justification', displayName:'Justification'},
                     {field:'delete', displayName:'', enableCellEdit: false, cellTemplate: "<md-button md-no-ink class='md-primary' ng-click='removeRow($event)'>x</md-button>"}]
    };
}]);