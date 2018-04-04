(function (angular) {
    var module = angular.module('app');
    module.controller('RoleListController', ['$scope', '$state', '$http', function ($scope, $state, $http) {
        $scope.pagination = {
            limit: 15,
            page: 1
        };

        $scope.query = {
            code: null,
            name: null,
            skip: 0,
            limit: 15
        };
        $scope.roleResponse = {};

        $scope.search = function () {
            var searchRequest = $scope.searchRequest();
            $http.put('/role', searchRequest).success(function (roleResponse) {
                $scope.roleResponse = roleResponse;
            });
        };

        $scope.searchRequest = function () {
            return {
                skip: $scope.pagination.limit * ($scope.pagination.page - 1),
                limit: $scope.pagination.limit,
                code: emptyToNull($scope.query.code),
                name: emptyToNull($scope.query.name)
            };
        };

        var emptyToNull = function (value) {
            if (!value || value == "") {
                return null;
            }
            return value;
        };

        $scope.search();
        $scope.viewRole = function (role) {
            $state.go('admin.role.detail', {code: role.code});
        };
    }]);
})(angular);