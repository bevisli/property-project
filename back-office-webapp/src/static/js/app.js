// Default colors
var brandPrimary = '#20a8d8';
var brandSuccess = '#4dbd74';
var brandInfo = '#63c2de';
var brandWarning = '#f8cb00';
var brandDanger = '#f86c6b';

var grayDark = '#2a2c36';
var gray = '#55595c';
var grayLight = '#818a91';
var grayLighter = '#d1d4d7';
var grayLightest = '#f8f9fa';

angular.module('app', [
    'ui.router',
    'ui.bootstrap',
    'oc.lazyLoad',
    'ngToast',
    'ngMessages',
    'ncy-angular-breadcrumb',
    'angular-loading-bar',
    'ngDialog',
    'ngFileUpload'
]).config(['cfpLoadingBarProvider', function (cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = false;
    cfpLoadingBarProvider.latencyThreshold = 1;
}]).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
}]).factory('httpInterceptor', ['$log', '$injector', '$q', 'ngToast', function ($log, $injector, $q, ngToast) {
    return {
        responseError: function (response) {
            if (response.status === 401) {
                location.href = "/home#!/login";
            } else {
                ngToast.error("HTTP request failed, please try again later!");
                return $q.reject(response);
            }
        }
    };
}]).service('toast', ['ngToast', function (ngToast) {
    var options = {className: '', content: '', timeout: 3000, dismissButton: true, animation: "fade"};
    return {
        'show': function (message) {
            ngToast.create(message);
        },
        'info': function (message) {
            options.className = 'info';
            options.content = message;
            ngToast.create(options);
        },
        'warn': function (message) {
            options.className = 'warning';
            options.content = message;
            ngToast.create(options);
        },
        'error': function (message) {
            options.className = 'danger';
            options.content = message;
            ngToast.create(options);
        },
        'success': function (message) {
            options.className = 'success';
            options.content = message;
            ngToast.create(options);
        }
    };
}]).service('dialog', ['ngDialog', function (ngDialog) {
    var options = {
        className: 'ngdialog-theme-default'
    };
    return {
        'open': function (opt) {
            return ngDialog.open(opt);
        },
        'show': function (opt) {
            options.$scope = opt.scope;
            options.id = opt.id;
            options.template = opt.templateURL;
            options.resolve = opt.resolve;
            options.controller = opt.controller;
            options.showClose = true;
            options.plain = false;
            options.closeByDocument = true;
            options.data = opt.data;
            return ngDialog.open(options);
        },
        'text': function (opt) {
            options.$scope = opt.$scope;
            options.id = opt.id;
            options.template = opt.plainText;
            options.resolve = opt.resolve;
            options.controller = opt.controller;
            options.plain = true;
            options.showClose = false;
            options.closeByDocument = true;
            return ngDialog.open(options);
        },
        "confirm": function (opt) {
            options.id = "globalConfirmDialog";
            options.template = "globalConfirmDialog";
            options.showClose = false;
            options.plain = false;
            options.closeByDocument = false;
            options.data = {
                title: opt.title,
                content: opt.content
            };
            return ngDialog.openConfirm(options);
        },
        "close": function (id, value) {
            ngDialog.close(id, value)
        }
    };
}]).run(['$rootScope', '$state', '$stateParams', function ($rootScope, $state, $stateParams) {
    $rootScope.$on('$stateChangeSuccess', function () {
        document.body.scrollTop = document.documentElement.scrollTop = 0;
    });
    $rootScope.$state = $state;
    return $rootScope.$stateParams = $stateParams;
}]);
