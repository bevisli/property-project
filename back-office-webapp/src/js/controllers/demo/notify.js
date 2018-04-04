(function (angular) {
    var module = angular.module('app');
    module.controller('DemoNotifyController', ['$scope', 'toast', function ($scope, toast) {
        $scope.info = function () {
            toast.info("this is an info message!");
        };
        $scope.error = function () {
            toast.error("this is an error message!");
        };
        $scope.warn = function () {
            toast.warn("this is a warn message!");
        };
        $scope.success = function () {
            toast.success("this is a success message!");
        };
    }]);
})(angular);