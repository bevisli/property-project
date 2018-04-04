(function (angular) {
    var module = angular.module('app');
    module.controller('UserSearchController', ['$scope', '$state', '$http', function ($scope, $state, $http) {
        $scope.filters = [
            {name: "用户名", code: "name", desc: "输入用户名"},
            {name: "电话号码", code: "phoneNumber", desc: "输入电话号码"}
        ];

        $scope.selectedFilter = $scope.filters[0];

        $scope.filterChanged = function () {
            $scope.query.filter.type = $scope.selectedFilter.code;
        };

        $scope.pagination = {
            total: 100,
            pageSize: 10,
            currentPage: 1,
            maxSize: 5,
            pageChanged: function () {
                $scope.search();
            }
        };

        $scope.query = {
            filter: {
                type: "",
                value: ""
            },
            skip: $scope.pagination.pageSize * ($scope.pagination.currentPage - 1),
            limit: $scope.pagination.pageSize
        };

        $scope.userResponse = {
            users: []
        };

        $scope.search = function () {
            // var searchRequest = $scope.searchRequest();
            // $http.put('/user', searchRequest).success(function (userResponse) {
            //     $scope.userResponse = userResponse;
            // });
            if ($scope.query.filter.type === "name") {
                $scope.userResponse.users = [
                    {
                        "id": "1",
                        "phone_number": "13950174314",
                        "name": "Mort",
                        "status": "ACTIVE"
                    }
                ];
            } else {
                $scope.userResponse.users = [
                    {
                        "id": "1",
                        "phone_number": "13950174314",
                        "name": "Mort",
                        "status": "ACTIVE"
                    },
                    {
                        "id": "2",
                        "phone_number": "13950174314",
                        "name": "Zheng",
                        "status": "INACTIVE"
                    }
                ];
            }
        };

        $scope.clear = function () {
            $scope.query.filter.value = null;
        };

        $scope.disable = function (id) {
            $http.put('/user/' + id + "/status", {status: "INACTIVE"}).success(function () {
                $scope.search();
            });
        };

        $scope.activate = function (id) {
            $http.put('/user/' + id + "/status", {status: "ACTIVE"}).success(function () {
                $scope.search();
            });
        };


        $scope.searchRequest = function () {
            return {
                skip: $scope.pagination.limit * ($scope.pagination.page - 1),
                limit: $scope.pagination.limit,
                phone_number: emptyToNull($scope.query.phoneNumber),
                name: emptyToNull($scope.query.name)
            };
        };

        var emptyToNull = function (value) {
            if (!value) {
                return null;
            }
            return value;
        };

        $scope.filterChanged();
        $scope.search();
    }]);
})(angular);