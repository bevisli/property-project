(function (angular) {
    var module = angular.module('app');
    module.controller('UserPasswordController', ['$scope', '$http', '$stateParams', '$mdDialog', '$mdToast', function ($scope, $http, $stateParams, $mdDialog, $mdToast) {
        $scope.user = {
            old_password: "",
            password: "",
            password_confirm: ""
        };

        $scope.resetPassword = function () {
            if ($scope.user.password != $scope.user.old_password) {
                $mdToast.show("密码不一致");
                $scope.user.password_confirm = "";
                return false;
            }
            var resetPasswordRequest = {
                password: $scope.user.password,
                old_password: $scope.user.old_password,
                requested_by: ""
            };
            $http.put('/reset-password', resetPasswordRequest).success(function () {
                $mdToast.show("密码修改成功");
            }).error(function () {
                $mdToast.show("旧密码错误");
            });
        };
    }]);
})(angular);