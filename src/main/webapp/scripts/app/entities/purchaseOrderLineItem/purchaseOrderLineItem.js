'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('purchaseOrderLineItem', {
                parent: 'entity',
                url: '/purchaseOrderLineItems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PurchaseOrderLineItems'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/purchaseOrderLineItem/purchaseOrderLineItems.html',
                        controller: 'PurchaseOrderLineItemController'
                    }
                },
                resolve: {
                }
            })
            .state('purchaseOrderLineItem.detail', {
                parent: 'entity',
                url: '/purchaseOrderLineItem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PurchaseOrderLineItem'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/purchaseOrderLineItem/purchaseOrderLineItem-detail.html',
                        controller: 'PurchaseOrderLineItemDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PurchaseOrderLineItem', function($stateParams, PurchaseOrderLineItem) {
                        return PurchaseOrderLineItem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('purchaseOrderLineItem.new', {
                parent: 'purchaseOrderLineItem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/purchaseOrderLineItem/purchaseOrderLineItem-dialog.html',
                        controller: 'PurchaseOrderLineItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    cost: null,
                                    listPrice: null,
                                    qtyOrdered: null,
                                    qtyDelivered: null,
                                    qtyPreviouslyDelivered: null,
                                    qtyImported: null,
                                    totalTaxCharge: null,
                                    discountPercentage: null,
                                    lineNo: null,
                                    listPriceDiscount: null,
                                    unitMeasure: null,
                                    isHidden: null,
                                    Ref1: null,
                                    Ref2: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('purchaseOrderLineItem', null, { reload: true });
                    }, function() {
                        $state.go('purchaseOrderLineItem');
                    })
                }]
            })
            .state('purchaseOrderLineItem.edit', {
                parent: 'purchaseOrderLineItem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/purchaseOrderLineItem/purchaseOrderLineItem-dialog.html',
                        controller: 'PurchaseOrderLineItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PurchaseOrderLineItem', function(PurchaseOrderLineItem) {
                                return PurchaseOrderLineItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('purchaseOrderLineItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
