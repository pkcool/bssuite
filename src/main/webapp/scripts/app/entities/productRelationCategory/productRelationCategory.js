'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productRelationCategory', {
                parent: 'entity',
                url: '/productRelationCategorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ProductRelationCategorys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productRelationCategory/productRelationCategorys.html',
                        controller: 'ProductRelationCategoryController'
                    }
                },
                resolve: {
                }
            })
            .state('productRelationCategory.detail', {
                parent: 'entity',
                url: '/productRelationCategory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ProductRelationCategory'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productRelationCategory/productRelationCategory-detail.html',
                        controller: 'ProductRelationCategoryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ProductRelationCategory', function($stateParams, ProductRelationCategory) {
                        return ProductRelationCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('productRelationCategory.new', {
                parent: 'productRelationCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productRelationCategory/productRelationCategory-dialog.html',
                        controller: 'ProductRelationCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('productRelationCategory', null, { reload: true });
                    }, function() {
                        $state.go('productRelationCategory');
                    })
                }]
            })
            .state('productRelationCategory.edit', {
                parent: 'productRelationCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productRelationCategory/productRelationCategory-dialog.html',
                        controller: 'ProductRelationCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProductRelationCategory', function(ProductRelationCategory) {
                                return ProductRelationCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productRelationCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('productRelationCategory.delete', {
                parent: 'productRelationCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productRelationCategory/productRelationCategory-delete-dialog.html',
                        controller: 'ProductRelationCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProductRelationCategory', function(ProductRelationCategory) {
                                return ProductRelationCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productRelationCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
