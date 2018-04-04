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
                templateUrl: 'views/common/layouts/full.html',
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
                            files: ['node_modules/font-awesome/css/font-awesome.min.css']
                        }, {
                            serie: true,
                            name: 'Simple Line Icons',
                            files: ['node_modules/simple-line-icons/css/simple-line-icons.css']
                        }, {
                            serie: true,
                            name: 'ngDialog',
                            files: ['node_modules/ng-dialog/css/ngDialog.min.css',
                                'node_modules/ng-dialog/css/ngDialog-theme-default.min.css']
                        }]);
                    }],
                    loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load files for an existing module
                        return $ocLazyLoad.load([{
                            serie: true,
                            name: 'chart.js',
                            files: [
                                'node_modules/chart.js/dist/Chart.min.js',
                                'node_modules/angular-chart.js/dist/angular-chart.min.js'
                            ]
                        }]);
                    }],
                }
            })
            .state('app.main', {
                url: '/dashboard',
                templateUrl: 'views/main.html',
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
                                    'node_modules/chart.js/dist/Chart.min.js',
                                    'node_modules/angular-chart.js/dist/angular-chart.min.js'
                                ]
                            },
                        ]);
                    }],
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['static/js/controllers/dashboard/main.js']
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
                templateUrl: 'views/user/search.html',
                controller: 'UserSearchController',
                ncyBreadcrumb: {
                    label: 'Search'
                },
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['static/js/controllers/user/search.js']
                        });
                    }]
                }
            })
            .state('app.admin.user.update', {
                url: "/update",
                templateUrl: 'views/user/update.html',
                controller: 'UserUpdateController',
                ncyBreadcrumb: {
                    label: 'Update'
                },
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load controllers
                        return $ocLazyLoad.load({
                            files: ['static/js/controllers/user/update.js']
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
                template: 'views/user/role.list.html',
                ncyBreadcrumb: {
                    label: 'Search'
                }
            })
            .state('appSimple', {
                abstract: true,
                templateUrl: 'views/common/layouts/simple.html',
                resolve: {
                    loadCSS: ['$ocLazyLoad', function ($ocLazyLoad) {
                        // you can lazy load CSS files
                        return $ocLazyLoad.load([{
                            serie: true,
                            name: 'Font Awesome',
                            files: ['node_modules/font-awesome/css/font-awesome.min.css']
                        }, {
                            serie: true,
                            name: 'Simple Line Icons',
                            files: ['node_modules/simple-line-icons/css/simple-line-icons.css']
                        }]);
                    }],
                }
            })
            // Additional Pages
            .state('appSimple.login', {
                url: '/login',
                templateUrl: 'views/login.html'
            })
            .state('appSimple.404', {
                url: '/404',
                templateUrl: 'views/404.html'
            })
            .state('appSimple.500', {
                url: '/500',
                templateUrl: 'views/500.html'
            })
    }]);
