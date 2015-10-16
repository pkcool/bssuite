'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('invoice', {
                parent: 'entity',
                url: '/invoices',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Invoices'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoice/invoices.html',
                        controller: 'InvoiceController'
                    }
                },
                resolve: {
                }
            })
            .state('invoice.detail', {
                parent: 'entity',
                url: '/invoice/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Invoice'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoice/invoice-detail.html',
                        controller: 'InvoiceDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Invoice', function($stateParams, Invoice) {
                        return Invoice.get({id : $stateParams.id});
                    }]
                }
            })
            .state('invoice.new', {
                parent: 'invoice',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/invoice/invoice-dialog.html',
                        controller: 'InvoiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    invoiceNo: null,
                                    invoiceTxnType: null,
                                    txnDate: null,
                                    dueDate: null,
                                    reference: null,
                                    ourRef: null,
                                    ourRef2: null,
                                    ourRef3: null,
                                    taxExemptionCode: null,
                                    isPenaltyIssued: null,
                                    freight: null,
                                    handlingCharge: null,
                                    charge2: null,
                                    isTaxable: null,
                                    isLocked: null,
                                    adjustTax: null,
                                    adjustTaxExempt: null,
                                    paymentCash: null,
                                    paymentCheque: null,
                                    paymentCreditCard: null,
                                    paymentDirectDeposit: null,
                                    paymentVoucher: null,
                                    paymentDirectDebit: null,
                                    payment1: null,
                                    payment2: null,
                                    bank: null,
                                    bankBranch: null,
                                    bankAccount: null,
                                    dateOfDeposit: null,
                                    drawerName: null,
                                    NoAlloc: null,
                                    coinRound: null,
                                    prepayment: null,
                                    in2: null,
                                    comment: null,
                                    taxAmount: null,
                                    total: null,
                                    cost: null,
                                    isOnHold: null,
                                    isLayBy: null,
                                    isExternalTxn: null,
                                    isSuspended: null,
                                    generatedFrom: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('invoice', null, { reload: true });
                    }, function() {
                        $state.go('invoice');
                    })
                }]
            })
            .state('invoice.edit', {
                parent: 'invoice',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/invoice/invoice-dialog.html',
                        controller: 'InvoiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Invoice', function(Invoice) {
                                return Invoice.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('invoice', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
