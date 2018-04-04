(function (angular) {
    var module = angular.module('app');
    module.controller('DemoFileUploadController', ['$scope', 'Upload', function ($scope, Upload) {
        $scope.upload = function (files) {
            if (files && files.length > 0) {
                var file = files[0];
                Upload.upload({
                    url: '/file/example-uploader',
                    file: file
                }).progress(function (evt) {
                }).success(function (data) {
                    $scope.imageKey = data.path;
                    $scope.imagePreviewURL = data.previewURL;
                });
            }
        };
    }]);
})(angular);