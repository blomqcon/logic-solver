var logicApp = angular.module('logicApp', [
  'ngMaterial',
  'ngRoute',
  'logicServices',
  'logicControllers'
]);
	  

logicApp.config(["$routeProvider", "$mdThemingProvider" ,function ($routeProvider, $mdThemingProvider) {
    $routeProvider.when("/", {
        templateUrl: "partials/home.html",
        controller: "HomeCtrl",
    }).when("/truth-table", {
        templateUrl: "partials/truth-table.html",
        controller: "TruthTableCtrl",
    }).when("/proof-validate", {
        templateUrl: "partials/proof-validate.html",
        controller: "ProofValidteCtrl",
    }).when("/proof-solve", {
        templateUrl: "partials/proof-solve.html",
        controller: "ProofSolveCtrl",
    }).otherwise({
        redirectTo: '/'
      });
    
    $mdThemingProvider.theme('default');
}]);