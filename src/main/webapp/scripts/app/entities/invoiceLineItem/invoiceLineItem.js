'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('invoiceLineItem', {
                parent: 'entity',
                url: '/invoiceLineItems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'InvoiceLineItems'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceLineItem/invoiceLineItems.html',
                        controller: 'InvoiceLineItemController'
                    }
                },
                resolve: {
                }
            })
            .state('invoiceLineItem.detail', {
                parent: 'entity',
                url: '/invoiceLineItem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'InvoiceLineItem'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceLineItem/invoiceLineItem-detail.html',
                        controller: 'InvoiceLineItemDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'InvoiceLineItem', function($stateParams, InvoiceLineItem) {
                        return InvoiceLineItem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('invoiceLineItem.new', {
                parent: 'invoiceLineItem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/invoiceLineItem/invoiceLineItem-dialog.html',
                        controller: 'InvoiceLineItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    cost: null,
                                    soldFor: null,
                                    qtyOrdered: null,
                                    qtySold: null,
                                    qtyReturned: null,
                                    qtyPicked: null,
                                    totalTaxCharge: null,
                                    discountPercentage: null,
                                    discountDescription: null,
                                    discountPriceGroupCode: null,
                                    lineNo: null,
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
                        $state.go('invoiceLineItem', null, { reload: true });
                    }, function() {
                        $state.go('invoiceLineItem');
                    })
                }]
            })
            .state('invoiceLineItem.edit', {
                parent: 'invoiceLineItem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/invoiceLineItem/invoiceLineItem-dialog.html',
                        controller: 'InvoiceLineItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InvoiceLineItem', function(InvoiceLineItem) {
                                return InvoiceLineItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('invoiceLineItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('invoiceLineItem.delete', {
                parent: 'invoiceLineItem',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/invoiceLineItem/invoiceLineItem-delete-dialog.html',
                        controller: 'InvoiceLineItemDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InvoiceLineItem', function(InvoiceLineItem) {
                                return InvoiceLineItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('invoiceLineItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
