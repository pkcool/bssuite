'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('supplierDiscountRule', {
                parent: 'entity',
                url: '/supplierDiscountRules',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SupplierDiscountRules'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supplierDiscountRule/supplierDiscountRules.html',
                        controller: 'SupplierDiscountRuleController'
                    }
                },
                resolve: {
                }
            })
            .state('supplierDiscountRule.detail', {
                parent: 'entity',
                url: '/supplierDiscountRule/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SupplierDiscountRule'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supplierDiscountRule/supplierDiscountRule-detail.html',
                        controller: 'SupplierDiscountRuleDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SupplierDiscountRule', function($stateParams, SupplierDiscountRule) {
                        return SupplierDiscountRule.get({id : $stateParams.id});
                    }]
                }
            })
            .state('supplierDiscountRule.new', {
                parent: 'supplierDiscountRule',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/supplierDiscountRule/supplierDiscountRule-dialog.html',
                        controller: 'SupplierDiscountRuleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    supplierCode: null,
                                    ruleNo: null,
                                    isAppliedToSales: null,
                                    isAppliedOnSpecialItemsOnly: null,
                                    customerCode: null,
                                    customerCategoryCode: null,
                                    productCode: null,
                                    startDate: null,
                                    endDate: null,
                                    qtyBreak: null,
                                    fromStockGroupCode: null,
                                    toStockGroupCode: null,
                                    taxCode: null,
                                    isAppliedWhenTaxExempt: null,
                                    storeCode: null,
                                    discountName: null,
                                    stockFamilyCode: null,
                                    cost: null,
                                    discountFormula: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('supplierDiscountRule', null, { reload: true });
                    }, function() {
                        $state.go('supplierDiscountRule');
                    })
                }]
            })
            .state('supplierDiscountRule.edit', {
                parent: 'supplierDiscountRule',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/supplierDiscountRule/supplierDiscountRule-dialog.html',
                        controller: 'SupplierDiscountRuleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SupplierDiscountRule', function(SupplierDiscountRule) {
                                return SupplierDiscountRule.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('supplierDiscountRule', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
