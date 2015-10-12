'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('customerCategory', {
                parent: 'entity',
                url: '/customerCategorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CustomerCategorys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customerCategory/customerCategorys.html',
                        controller: 'CustomerCategoryController'
                    }
                },
                resolve: {
                }
            })
            .state('customerCategory.detail', {
                parent: 'entity',
                url: '/customerCategory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CustomerCategory'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customerCategory/customerCategory-detail.html',
                        controller: 'CustomerCategoryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'CustomerCategory', function($stateParams, CustomerCategory) {
                        return CustomerCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('customerCategory.new', {
                parent: 'customerCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/customerCategory/customerCategory-dialog.html',
                        controller: 'CustomerCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('customerCategory', null, { reload: true });
                    }, function() {
                        $state.go('customerCategory');
                    })
                }]
            })
            .state('customerCategory.edit', {
                parent: 'customerCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/customerCategory/customerCategory-dialog.html',
                        controller: 'CustomerCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CustomerCategory', function(CustomerCategory) {
                                return CustomerCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('customerCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
