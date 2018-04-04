(function (angular) {
    var module = angular.module('app');
    module.controller('RoleViewController', ['$scope', '$http', '$stateParams', '$mdDialog', '$mdToast','$state',  function ($scope, $http, $stateParams, $mdDialog, $mdToast, $state) {
        $scope.openToast = function (content) {
            $mdToast.show($mdToast.simple().textContent(content).position('top').hideDelay(3 * 1000));
        };

        if ($stateParams.code) {
            $scope.isUpdate = true;
            $http.get('/role/' + $stateParams.code).success(function (role) {
                $scope.role = role;
            }).error(function(err) {
                $scope.openToast(err.message);
            });
        }

        $scope.updateRole = function () {
            var request = {name: $scope.role.name, requested_by: ''};
            $http.put("/role/" + $scope.role.code, request).success(function () {
                $scope.openToast("更新成功");
            });
        };

        $scope.createRole = function () {
            var request = {code: $scope.role.code, name: $scope.role.name, requested_by: ''};
            $http.post("/role", request).success(function () {
                $scope.openToast("创建成功");
                $state.go('admin.role.list');
            }).error(function (response){
                $scope.openToast(response.error_message);
            });
        };

    }]);
})(angular);
