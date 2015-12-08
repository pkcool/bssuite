'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('product', {
                parent: 'entity',
                url: '/products',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Products'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/product/products.html',
                        controller: 'ProductController'
                    }
                },
                resolve: {
                }
            })
            .state('product.detail', {
                parent: 'entity',
                url: '/product/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Product'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/product/product-detail.html',
                        controller: 'ProductDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Product', function($stateParams, Product) {
                        return Product.get({id : $stateParams.id});
                    }]
                }
            })
            .state('product.new', {
                parent: 'product',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/product/product-dialog.html',
                        controller: 'ProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    longDescription: null,
                                    alternateCode: null,
                                    bin: null,
                                    isOnSpecial: null,
                                    isOnHold: null,
                                    isInPricelistReports: null,
                                    qtyOnOrder: null,
                                    qtyStockOnHold: null,
                                    qtyBackordered: null,
                                    qtyAllocated: null,
                                    qtyBackorderHold: null,
                                    qtyConsigned: null,
                                    qtyWarehouseReceived: null,
                                    qtyStocktakeVariance: null,
                                    qtyTransitIn: null,
                                    qtyTransitOut: null,
                                    cost: null,
                                    wholesaleListPrice: null,
                                    listPrice: null,
                                    tradePrice: null,
                                    boxCost: null,
                                    unitMeasure: null,
                                    boxMeasure: null,
                                    boxConversionFactor: null,
                                    weight: null,
                                    volumn: null,
                                    serviceCover: null,
                                    qtyFloorLevel: null,
                                    qtyReorderLevel: null,
                                    qtyOverstockLevel: null,
                                    isComment: null,
                                    isDiminishing: null,
                                    isNonTaxExeptable: null,
                                    leadTime: null,
                                    purchaseUnit: null,
                                    estMonthlySales: null,
                                    dateFirstSale: null,
                                    dateLastSale: null,
                                    dateFirstOrder: null,
                                    dateCreated: null,
                                    dateLastDelivery: null,
                                    dateNextDelivery: null,
                                    dateLastTransfer: null,
                                    dateLastOrder: null,
                                    dateLastStocktake: null,
                                    isArchived: null,
                                    classCode: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('product');
                    })
                }]
            })
            .state('product.edit', {
                parent: 'product',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/product/product-dialog.html',
                        controller: 'ProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Product', function(Product) {
                                return Product.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('product.delete', {
                parent: 'product',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/product/product-delete-dialog.html',
                        controller: 'ProductDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Product', function(Product) {
                                return Product.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
