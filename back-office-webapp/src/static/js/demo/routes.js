angular.module('app')
    .config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider', '$breadcrumbProvider', function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $breadcrumbProvider) {
        $stateProvider
            .state('app.demo', {
                url: "/demo",
                abstract: true,
                template: '<ui-view></ui-view>',
                ncyBreadcrumb: {
                    label: 'Demos'
                }
            })
            .state('app.demo.dialog', {
                url: '/dialog',
                templateUrl: 'views/demo/dialog.html',
                controller: "DemoDialogController",
                ncyBreadcrumb: {
                    label: 'Dialogs'
                },
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['static/js/controllers/demo/dialog.js',
                                'static/js/controllers/demo/dialog-in.js']
                        });
                    }]
                }
            })
            .state('app.demo.file', {
                url: '/file',
                templateUrl: 'views/demo/file.html',
                controller: "DemoFileUploadController",
                ncyBreadcrumb: {
                    label: 'File'
                },
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['static/js/controllers/demo/file.js']
                        });
                    }]
                }
            })
            .state('app.demo.form', {
                url: '/form',
                templateUrl: 'views/demo/form.html',
                controller: "DemoFormController",
                ncyBreadcrumb: {
                    label: 'Form'
                },
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['static/js/controllers/demo/form.js']
                        });
                    }]
                }
            })
            .state('app.demo.pagination', {
                url: '/pagination',
                templateUrl: 'views/demo/pagination.html',
                controller: "DemoPaginationController",
                ncyBreadcrumb: {
                    label: 'Pagination'
                },
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['static/js/controllers/demo/pagination.js']
                        });
                    }]
                }
            })
            .state('app.demo.notify', {
                url: '/notify',
                templateUrl: 'views/demo/notify.html',
                controller: "DemoNotifyController",
                ncyBreadcrumb: {
                    label: 'Notify'
                },
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['static/js/controllers/demo/notify.js']
                        });
                    }]
                }
            })
    }]);
