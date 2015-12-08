'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('salesOrder', {
                parent: 'entity',
                url: '/salesOrders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SalesOrders'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/salesOrder/salesOrders.html',
                        controller: 'SalesOrderController'
                    }
                },
                resolve: {
                }
            })
            .state('salesOrder.detail', {
                parent: 'entity',
                url: '/salesOrder/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SalesOrder'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/salesOrder/salesOrder-detail.html',
                        controller: 'SalesOrderDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SalesOrder', function($stateParams, SalesOrder) {
                        return SalesOrder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('salesOrder.new', {
                parent: 'salesOrder',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/salesOrder/salesOrder-dialog.html',
                        controller: 'SalesOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    orderNo: null,
                                    status: null,
                                    txnDate: null,
                                    forwardDate: null,
                                    requiredDate: null,
                                    customerOrderNo: null,
                                    ourRef: null,
                                    freight: null,
                                    handlingCharge: null,
                                    charge2: null,
                                    isTaxable: null,
                                    isLocked: null,
                                    adjustTax: null,
                                    adjustTaxExempt: null,
                                    prepayment: null,
                                    prepaymentNo: null,
                                    comment: null,
                                    totalTaxAmount: null,
                                    totalSellPrice: null,
                                    totalCost: null,
                                    isSuspended: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('salesOrder', null, { reload: true });
                    }, function() {
                        $state.go('salesOrder');
                    })
                }]
            })
            .state('salesOrder.edit', {
                parent: 'salesOrder',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/salesOrder/salesOrder-dialog.html',
                        controller: 'SalesOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SalesOrder', function(SalesOrder) {
                                return SalesOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('salesOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('salesOrder.delete', {
                parent: 'salesOrder',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/salesOrder/salesOrder-delete-dialog.html',
                        controller: 'SalesOrderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SalesOrder', function(SalesOrder) {
                                return SalesOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('salesOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
