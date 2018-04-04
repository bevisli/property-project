(function (angular) {
    var module = angular.module('app');
    module.controller('DemoDialogInController', ['$scope', 'toast', function ($scope, toast) {
        $scope.notify = function () {
            toast.success("data sent from parent:" + $scope.ngDialogData.data1);
        }
    }]);
})(angular);