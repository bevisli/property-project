angular.module('app')
    .config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider', '$breadcrumbProvider', function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $breadcrumbProvider) {
        $urlRouterProvider.otherwise('/dashboard');

        $ocLazyLoadProvider.config({
            // Set to true if you want to see what and when is dynamically loaded
            debug: true
        });

        $breadcrumbProvider.setOptions({
            prefixStateName: 'app.main',
            includeAbstract: true,
            template: '<li class="breadcrumb-item" ng-repeat="step in steps" ng-class="{active: $last}" ng-switch="$last || !!step.abstract"><a ng-switch-when="false" href="{{step.ncyBreadcrumbLink}}">{{step.ncyBreadcrumbLabel}}</a><span ng-switch-when="true">{{step.ncyBreadcrumbLabel}}</span></li>'
        });

        $stateProvider
            .state('app', {
                abstract: true,
                templateUrl: '/template/views/common/layouts/full.html',
                //page title goes here
                ncyBreadcrumb: {
                    label: 'Root',
                    skip: true
                },
                resolve: {
                    loadCSS: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load CSS files
                        return $ocLazyLoad.load([{
                            serie: true,
                            name: 'Font Awesome',
                            files: ['/static/vendors/css/font-awesome.min.css']
                        }, {
                            serie: true,
                            name: 'Simple Line Icons',
                            files: ['/static/vendors/css/simple-line-icons.min.css']
                        }, {
                            serie: true,
                            name: 'ngDialog',
                            files: ['/static/vendors/css/ngDialog.min.css',
                                '/static/vendors/css/ngDialog-theme-default.min.css',
                                '/static/vendors/css/ngDialog-theme-plain.min.css',
                                '/static/vendors/css/ngDialog-custom-width.css']
                        }]);
                    }],
                    loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load files for an existing module
                        return $ocLazyLoad.load([{
                            serie: true,
                            name: 'chart.js',
                            files: [
                                '/static/vendors/js/Chart.min.js',
                                '/static/vendors/js/angular-chart.min.js'
                            ]
                        }]);
                    }],
                }
            })
            .state('app.main', {
                url: '/dashboard',
                templateUrl: '/template/views/main.html',
                //page title goes here
                ncyBreadcrumb: {
                    label: 'Home'
                },
                //page subtitle goes here
                params: {subtitle: 'Welcome to ASAMxpress BackOffice'},
                resolve: {
                    loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load files for an existing module
                        return $ocLazyLoad.load([
                            {
                                serie: true,
                                name: 'chart.js',
                                files: [
                                    '/static/vendors/js/Chart.min.js',
                                    '/static/vendors/js/angular-chart.min.js'
                                ]
                            },
                        ]);
                    }],
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['/static/js/controllers/dashboard/main.js']
                        });
                    }]
                }
            })
            .state('app.csr', {
                url: "/csr",
                abstract: true,
                template: '<ui-view></ui-view>',
                ncyBreadcrumb: {
                    label: 'Customer Service'
                }
            })
            .state('app.csr.customer', {
                url: "/customer",
                abstract: true,
                template: '<ui-view></ui-view>',
                ncyBreadcrumb: {
                    label: 'Customers'
                }
            })
            .state('app.csr.customer.search', {
                url: "/search",
                template: '<ui-view></ui-view>',
                ncyBreadcrumb: {
                    label: 'search'
                }
            })
            .state('app.csr.order', {
                url: "/order",
                abstract: true,
                template: '<ui-view></ui-view>',
                ncyBreadcrumb: {
                    label: 'Orders'
                }
            })
            .state('app.csr.order.search', {
                url: "/search",
                template: '<ui-view></ui-view>',
                ncyBreadcrumb: {
                    label: 'Search'
                }
            })
            .state('app.admin', {
                url: "/admin",
                abstract: true,
                template: '<ui-view></ui-view>',
                ncyBreadcrumb: {
                    label: 'Admin'
                }
            })
            .state('app.admin.user', {
                url: "/user",
                abstract: true,
                template: '<ui-view></ui-view>',
                ncyBreadcrumb: {
                    label: 'Users'
                }
            })
            .state('app.admin.user.search', {
                url: "/search",
                templateUrl: '/template/views/user/search.html',
                controller: 'UserSearchController',
                ncyBreadcrumb: {
                    label: 'Search'
                },
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['/static/js/controllers/user/search.js']
                        });
                    }]
                }
            })
            .state('app.admin.user.update', {
                url: "/update",
                templateUrl: '/template/views/user/update.html',
                controller: 'UserUpdateController',
                ncyBreadcrumb: {
                    label: 'Update'
                },
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['/static/js/controllers/user/update.js']
                        });
                    }]
                }
            })
            .state('app.admin.role', {
                url: "/role",
                abstract: true,
                template: '<ui-view></ui-view>',
                ncyBreadcrumb: {
                    label: 'Roles'
                }
            })
            .state('app.admin.role.search', {
                url: "/search",
                template: '/template/views/user/role.list.html',
                ncyBreadcrumb: {
                    label: 'Search'
                }
            })
            .state('appSimple', {
                abstract: true,
                templateUrl: '/template/views/common/layouts/simple.html',
                resolve: {
                    loadCSS: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load CSS files
                        return $ocLazyLoad.load([{
                            serie: true,
                            name: 'Font Awesome',
                            files: ['/static/vendors/css/font-awesome.min.css']
                        }, {
                            serie: true,
                            name: 'Simple Line Icons',
                            files: ['/static/vendors/css/simple-line-icons.min.css']
                        }]);
                    }],
                }
            })
            // Additional Pages
            .state('appSimple.login', {
                url: '/login',
                templateUrl: '/template/login.html'
            })
            .state('appSimple.404', {
                url: '/404',
                templateUrl: '/template/404.html'
            })
            .state('appSimple.500', {
                url: '/500',
                templateUrl: '/template/500.html'
            })
    }]);
