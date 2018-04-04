(function (angular) {
    var module = angular.module('app');
    module.controller('DemoFormController', ['$scope', 'toast', function ($scope, toast) {
        $scope.user = {};
        $scope.submit = function () {
            toast.success("submit user successfully!");
        };

        $scope.clear = function () {
            $scope.user.name = null;
            $scope.user.phone_number = null;
            $scope.user.password = null;
        };
    }]);
})(angular);