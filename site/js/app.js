var logicApp = angular.module('logicApp', [
  'ngMaterial',
  'ngRoute',
  'logicServices',
  'logicControllers'
]);

logicApp.config(["$routeProvider",function ($routeProvider) {
    $routeProvider.when("/", {
        templateUrl: "partials/home.html",
        controller: "HomeCtrl",
    }).when("/test", {
        templateUrl: "partials/test.html",
        controller: "TestCtrl",
    }).otherwise({
        redirectTo: '/'
      });
}]);