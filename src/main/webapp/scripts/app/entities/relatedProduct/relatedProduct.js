'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('relatedProduct', {
                parent: 'entity',
                url: '/relatedProducts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'RelatedProducts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/relatedProduct/relatedProducts.html',
                        controller: 'RelatedProductController'
                    }
                },
                resolve: {
                }
            })
            .state('relatedProduct.detail', {
                parent: 'entity',
                url: '/relatedProduct/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'RelatedProduct'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/relatedProduct/relatedProduct-detail.html',
                        controller: 'RelatedProductDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'RelatedProduct', function($stateParams, RelatedProduct) {
                        return RelatedProduct.get({id : $stateParams.id});
                    }]
                }
            })
            .state('relatedProduct.new', {
                parent: 'relatedProduct',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/relatedProduct/relatedProduct-dialog.html',
                        controller: 'RelatedProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    isSuggested: null,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('relatedProduct', null, { reload: true });
                    }, function() {
                        $state.go('relatedProduct');
                    })
                }]
            })
            .state('relatedProduct.edit', {
                parent: 'relatedProduct',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/relatedProduct/relatedProduct-dialog.html',
                        controller: 'RelatedProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RelatedProduct', function(RelatedProduct) {
                                return RelatedProduct.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('relatedProduct', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('relatedProduct.delete', {
                parent: 'relatedProduct',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/relatedProduct/relatedProduct-delete-dialog.html',
                        controller: 'RelatedProductDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RelatedProduct', function(RelatedProduct) {
                                return RelatedProduct.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('relatedProduct', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
