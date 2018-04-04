(function (angular) {
    var module = angular.module('app');
    module.controller('UserUpdateController', ['$scope', '$http', '$stateParams', '$state', 'toast', function ($scope, $http, $stateParams, $state, toast) {
        $scope.userResponse = {
            user: {},
            role_total: 0,
            roles: [
                {code: "AD", name: "管理员"},
                {code: "CS", name: "客服人员"}
            ]
        };

        $scope.getUser = function () {
            $http.get('/user/' + $stateParams.id).success(function (userResponse) {
                $scope.userResponse = userResponse;
            });
        };

        $scope.updateUser = function () {
            var request = {
                password: $scope.userResponse.user.password,
                name: $scope.userResponse.user.name
            };
            $http.put("/user/" + $stateParams.id, request).success(function () {
                toast.success("update success");
                $state.go('app.admin.user.search');
            })
        };

        $scope.createUser = function () {
            var request = {
                phone_number: $scope.userResponse.user.phone_number,
                password: $scope.userResponse.user.password,
                name: $scope.userResponse.user.name
            };
            $http.post("/user", request).success(function () {
                $state.go('app.admin.user.search');
            }).error(function (err) {
                if (err.error_code === "PHONE_NUMBER_FORBIDDEN") {
                    toast.warn("该手机号已注册");
                } else {
                    toast.error("系统错误");
                }
            });
        };

        $scope.addRole = function (code) {
            var request = {
                code: code,
                role_action: "ASSIGN"
            };
            $http.put("/user/" + $stateParams.id + "/role", request).success(function () {
                $scope.getUser();
            })
        };

        $scope.removeRole = function (code) {
            var request = {
                code: code,
                role_action: "UNASSIGN"
            };
            $http.put("/user/" + $stateParams.id + "/role", request).success(function () {
                $scope.getUser();
            })
        };

        $scope.showMessage = function () {
            toast.success("saved!")
        };

        if ($stateParams.id) {
            $scope.isUpdate = true;
            $scope.getUser();
        }
    }]);
})(angular);