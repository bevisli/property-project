(function (angular) {
    var module = angular.module('app');
    module.controller('DemoDialogController', ['$scope', 'toast', 'dialog', function ($scope, toast, dialog) {
        $scope.show = function () {
            dialog.show({
                    id: "demo-dialog",
                    scope: $scope,
                    templateURL: "/template/views/demo/dialog-in.html",
                    controller: "DemoDialogInController",
                    data: {
                        data1: "value1"
                    },
                    resolve: {
                        toast: function () {
                            return toast;
                        }
                    }
                }
            ).closePromise.then(function (obj) {
                toast.info("return value from dialog, val=" + obj.value);
            });
        };
        $scope.text = function () {
            dialog.text({
                id: "demo-dialog",
                scope: $scope,
                plainText: "This is a dialog with plain text!",
                controller: "DemoDialogInController",
                resolve: null
            }).closePromise.then(function () {
                toast.info("close text dialog");
            });
        };
        $scope.confirm = function () {
            dialog.confirm({
                title: "Dialog Demo Title",
                content: "Dialog Demo Content"
            }).then(function () {
                toast.info("click yes on confirm dialog");
            }, function (data) {
                toast.warn("click no on confirm dialog, val:" + data);
            });
        }
    }]);
})(angular);