'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('goodsReceivedAudit', {
                parent: 'entity',
                url: '/goodsReceivedAudits',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'GoodsReceivedAudits'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/goodsReceivedAudit/goodsReceivedAudits.html',
                        controller: 'GoodsReceivedAuditController'
                    }
                },
                resolve: {
                }
            })
            .state('goodsReceivedAudit.detail', {
                parent: 'entity',
                url: '/goodsReceivedAudit/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'GoodsReceivedAudit'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/goodsReceivedAudit/goodsReceivedAudit-detail.html',
                        controller: 'GoodsReceivedAuditDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'GoodsReceivedAudit', function($stateParams, GoodsReceivedAudit) {
                        return GoodsReceivedAudit.get({id : $stateParams.id});
                    }]
                }
            })
            .state('goodsReceivedAudit.new', {
                parent: 'goodsReceivedAudit',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/goodsReceivedAudit/goodsReceivedAudit-dialog.html',
                        controller: 'GoodsReceivedAuditDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    receivedOn: null,
                                    txnNumber: null,
                                    typeReceipt: null,
                                    qtyReceived: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('goodsReceivedAudit', null, { reload: true });
                    }, function() {
                        $state.go('goodsReceivedAudit');
                    })
                }]
            })
            .state('goodsReceivedAudit.edit', {
                parent: 'goodsReceivedAudit',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/goodsReceivedAudit/goodsReceivedAudit-dialog.html',
                        controller: 'GoodsReceivedAuditDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GoodsReceivedAudit', function(GoodsReceivedAudit) {
                                return GoodsReceivedAudit.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('goodsReceivedAudit', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
