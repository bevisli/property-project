(function (angular) {
    var module = angular.module('app');
    module.controller('DemoPaginationController', ['$scope', 'toast', function ($scope, toast) {
        $scope.pagination = {
            total: 1000,
            pageSize: 20,
            currentPage: 1,
            maxSize: 5,
            pageChanged: function () {
                $scope.search();
            }
        };

        $scope.search = function () {
            $scope.users = [];
            var skip = $scope.pagination.pageSize * ($scope.pagination.currentPage - 1) + 1;
            var to = skip + $scope.pagination.pageSize - 1;
            for (var i = skip; i <= to; i++) {
                var user = {};
                user.id = i;
                user.name = "name" + i;
                user.phone_number = "139xxxxxx" + i;
                user.status = i % 2 ? "A" : "I";
                $scope.users.push(user);
            }
        };

        $scope.search();
    }]);
})(angular);