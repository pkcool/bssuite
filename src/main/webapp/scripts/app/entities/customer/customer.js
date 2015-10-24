'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('customer', {
                parent: 'entity',
                url: '/customers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.customer.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customer/customers.html',
                        controller: 'CustomerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('customer');
                        $translatePartialLoader.addPart('customerAgeingMethod');
                        $translatePartialLoader.addPart('creditCardType');
                        $translatePartialLoader.addPart('customerAccountType');
                        $translatePartialLoader.addPart('customerInvoiceDeliveryMethod');
                        $translatePartialLoader.addPart('customerStatementDeliveryMethod');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('customer.detail', {
                parent: 'entity',
                url: '/customer/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.customer.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customer/customer-detail.html',
                        controller: 'CustomerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('customer');
                        $translatePartialLoader.addPart('customerAgeingMethod');
                        $translatePartialLoader.addPart('creditCardType');
                        $translatePartialLoader.addPart('customerAccountType');
                        $translatePartialLoader.addPart('customerInvoiceDeliveryMethod');
                        $translatePartialLoader.addPart('customerStatementDeliveryMethod');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Customer', function($stateParams, Customer) {
                        return Customer.get({id : $stateParams.id});
                    }]
                }
            })
            .state('customer.new', {
                parent: 'customer',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/customer/customer-dialog.html',
                        controller: 'CustomerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    tradingName: null,
                                    abn: null,
                                    deliveryInstruction: null,
                                    webUrl: null,
                                    isOnHold: null,
                                    isOrderNoRequired: null,
                                    isBlacklisted: null,
                                    isBackorderAllowed: null,
                                    isArchived: null,
                                    isHeadOfficeAccount: null,
                                    ageingMethod: null,
                                    isWebaccessAllowed: null,
                                    bankName: null,
                                    bsb: null,
                                    bankAccountNo: null,
                                    bankSuburb: null,
                                    bankAccountName: null,
                                    isCreditCardBillingActive: null,
                                    creditCardNo: null,
                                    creditCardHolderName: null,
                                    creditCardExpiryDate: null,
                                    creditCardType: null,
                                    accountType: null,
                                    creditAmount: null,
                                    terms: null,
                                    settlementTerms: null,
                                    invoiceDeliveryMethod: null,
                                    statementDeliveryMethod: null,
                                    invoiceEmailAddress: null,
                                    invoiceFaxNo: null,
                                    statementEmailAddress: null,
                                    statementFaxNo: null,
                                    isPenaltyIssued: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('customer', null, { reload: true });
                    }, function() {
                        $state.go('customer');
                    })
                }]
            })
            .state('customer.edit', {
                parent: 'customer',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/customer/customer-dialog.html',
                        controller: 'CustomerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Customer', function(Customer) {
                                return Customer.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('customer', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
