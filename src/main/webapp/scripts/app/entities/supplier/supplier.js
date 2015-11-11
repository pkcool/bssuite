'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('supplier', {
                parent: 'entity',
                url: '/suppliers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Suppliers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supplier/suppliers.html',
                        controller: 'SupplierController'
                    }
                },
                resolve: {
                }
            })
            .state('supplier.detail', {
                parent: 'entity',
                url: '/supplier/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Supplier'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supplier/supplier-detail.html',
                        controller: 'SupplierDetailController'
                    }
                },
                resolve: {
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
            })
            .state('supplier.delete', {
                parent: 'supplier',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/supplier/supplier-delete-dialog.html',
                        controller: 'SupplierDeleteController',
                        size: 'md',
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
