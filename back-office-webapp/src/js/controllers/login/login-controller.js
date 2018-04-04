(function (angular) {
    var module = angular.module('app');
    module.controller('LoginController', ['$rootScope', '$scope', '$state', '$http', function ($rootScope, $scope, $state, $http) {
        $scope.loginConfirm = function () {
            var loginRequest = {email: $scope.user.email, password: $scope.user.password};
            $http.post("/loginConfirm", loginRequest).success(function (response) {
                if (response.is_success) {
                    $state.go("admin");
                } else {
                    // todo Login failed and show message
                }
            })
        };
    }]);
})(angular);