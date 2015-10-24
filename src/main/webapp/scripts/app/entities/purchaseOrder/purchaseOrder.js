'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('purchaseOrder', {
                parent: 'entity',
                url: '/purchaseOrders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.purchaseOrder.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/purchaseOrder/purchaseOrders.html',
                        controller: 'PurchaseOrderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('purchaseOrder');
                        $translatePartialLoader.addPart('purchaseOrderStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('purchaseOrder.detail', {
                parent: 'entity',
                url: '/purchaseOrder/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.purchaseOrder.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/purchaseOrder/purchaseOrder-detail.html',
                        controller: 'PurchaseOrderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('purchaseOrder');
                        $translatePartialLoader.addPart('purchaseOrderStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PurchaseOrder', function($stateParams, PurchaseOrder) {
                        return PurchaseOrder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('purchaseOrder.new', {
                parent: 'purchaseOrder',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/purchaseOrder/purchaseOrder-dialog.html',
                        controller: 'PurchaseOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    orderNo: null,
                                    status: null,
                                    createdDate: null,
                                    ref: null,
                                    expectedDeliveryDate: null,
                                    isTaxable: null,
                                    isLocked: null,
                                    comment: null,
                                    totalTaxAmount: null,
                                    totalCost: null,
                                    taxExemptionCode: null,
                                    isSuspended: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('purchaseOrder', null, { reload: true });
                    }, function() {
                        $state.go('purchaseOrder');
                    })
                }]
            })
            .state('purchaseOrder.edit', {
                parent: 'purchaseOrder',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/purchaseOrder/purchaseOrder-dialog.html',
                        controller: 'PurchaseOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PurchaseOrder', function(PurchaseOrder) {
                                return PurchaseOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('purchaseOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
