'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('supplier', {
                parent: 'entity',
                url: '/suppliers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.supplier.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supplier/suppliers.html',
                        controller: 'SupplierController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('supplier');
                        $translatePartialLoader.addPart('supplierAccountType');
                        $translatePartialLoader.addPart('supplierAgeingMethod');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('supplier.detail', {
                parent: 'entity',
                url: '/supplier/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.supplier.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supplier/supplier-detail.html',
                        controller: 'SupplierDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('supplier');
                        $translatePartialLoader.addPart('supplierAccountType');
                        $translatePartialLoader.addPart('supplierAgeingMethod');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Supplier', function($stateParams, Supplier) {
                        return Supplier.get({id : $stateParams.id});
                    }]
                }
            })
            .state('supplier.new', {
                parent: 'supplier',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/supplier/supplier-dialog.html',
                        controller: 'SupplierDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    webUrl: null,
                                    comment: null,
                                    isOnHold: null,
                                    isHeadOffice: null,
                                    leadTime: null,
                                    accountType: null,
                                    settlementTerms: null,
                                    credit: null,
                                    terms: null,
                                    ageingMethod: null,
                                    isEFTPaymentsUsed: null,
                                    bankBSB: null,
                                    bankNumber: null,
                                    bankAccount: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('supplier', null, { reload: true });
                    }, function() {
                        $state.go('supplier');
                    })
                }]
            })
            .state('supplier.edit', {
                parent: 'supplier',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/supplier/supplier-dialog.html',
                        controller: 'SupplierDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Supplier', function(Supplier) {
                                return Supplier.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('supplier', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
