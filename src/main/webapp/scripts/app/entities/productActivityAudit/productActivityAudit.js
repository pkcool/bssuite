'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productActivityAudit', {
                parent: 'entity',
                url: '/productActivityAudits',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ProductActivityAudits'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productActivityAudit/productActivityAudits.html',
                        controller: 'ProductActivityAuditController'
                    }
                },
                resolve: {
                }
            })
            .state('productActivityAudit.detail', {
                parent: 'entity',
                url: '/productActivityAudit/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ProductActivityAudit'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productActivityAudit/productActivityAudit-detail.html',
                        controller: 'ProductActivityAuditDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ProductActivityAudit', function($stateParams, ProductActivityAudit) {
                        return ProductActivityAudit.get({id : $stateParams.id});
                    }]
                }
            })
            .state('productActivityAudit.new', {
                parent: 'productActivityAudit',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/productActivityAudit/productActivityAudit-dialog.html',
                        controller: 'ProductActivityAuditDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    createdOn: null,
                                    txnNumber: null,
                                    activityType: null,
                                    qtyTxn: null,
                                    qtyAdjusted: null,
                                    qtyStockOnHold: null,
                                    lineNo: null,
                                    txnAccountCode: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('productActivityAudit', null, { reload: true });
                    }, function() {
                        $state.go('productActivityAudit');
                    })
                }]
            })
            .state('productActivityAudit.edit', {
                parent: 'productActivityAudit',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/productActivityAudit/productActivityAudit-dialog.html',
                        controller: 'ProductActivityAuditDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProductActivityAudit', function(ProductActivityAudit) {
                                return ProductActivityAudit.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productActivityAudit', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('productActivityAudit.delete', {
                parent: 'productActivityAudit',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/productActivityAudit/productActivityAudit-delete-dialog.html',
                        controller: 'ProductActivityAuditDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProductActivityAudit', function(ProductActivityAudit) {
                                return ProductActivityAudit.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productActivityAudit', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
