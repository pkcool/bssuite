'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('salesOrderLineItem', {
                parent: 'entity',
                url: '/salesOrderLineItems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.salesOrderLineItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/salesOrderLineItem/salesOrderLineItems.html',
                        controller: 'SalesOrderLineItemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('salesOrderLineItem');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('salesOrderLineItem.detail', {
                parent: 'entity',
                url: '/salesOrderLineItem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.salesOrderLineItem.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/salesOrderLineItem/salesOrderLineItem-detail.html',
                        controller: 'SalesOrderLineItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('salesOrderLineItem');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SalesOrderLineItem', function($stateParams, SalesOrderLineItem) {
                        return SalesOrderLineItem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('salesOrderLineItem.new', {
                parent: 'salesOrderLineItem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/salesOrderLineItem/salesOrderLineItem-dialog.html',
                        controller: 'SalesOrderLineItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    cost: null,
                                    soldFor: null,
                                    qtyOrdered: null,
                                    qtyAllocated: null,
                                    totalTaxCharge: null,
                                    discountPercentage: null,
                                    lineNo: null,
                                    requiredDate: null,
                                    listPrice: null,
                                    listPriceDiscount: null,
                                    cost2: null,
                                    isHidden: null,
                                    Ref1: null,
                                    Ref2: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('salesOrderLineItem', null, { reload: true });
                    }, function() {
                        $state.go('salesOrderLineItem');
                    })
                }]
            })
            .state('salesOrderLineItem.edit', {
                parent: 'salesOrderLineItem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/salesOrderLineItem/salesOrderLineItem-dialog.html',
                        controller: 'SalesOrderLineItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SalesOrderLineItem', function(SalesOrderLineItem) {
                                return SalesOrderLineItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('salesOrderLineItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
