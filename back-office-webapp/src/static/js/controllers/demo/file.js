(function (angular) {
    var module = angular.module('app');
    module.controller('DemoFileUploadController', ['$scope', 'Upload', '$timeout', function ($scope, Upload, $timeout) {
        $scope.upload = function (file, errorFiles) {
            $scope.targetFile = file;
            $scope.errorFile = errorFiles && errorFiles[0];
            if (file) {
                Upload.upload({
                    url: '/file/example-uploader',
                    data: {file: file}
                }).then(function (success) {
                    $scope.imageKey = success.path;
                    $scope.imagePreviewURL = success.previewURL;
                }, function (error) {
                    $scope.errorMsg = "status=" + error.status + ", message=" + error.data;
                }, function (progress) {
                    file.progress = Math.min(100, parseInt(100.0 * progress.loaded / progress.total));
                });
            }
        }
    }]);
})(angular);