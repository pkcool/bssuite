'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('quoteLineItem', {
                parent: 'entity',
                url: '/quoteLineItems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'QuoteLineItems'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quoteLineItem/quoteLineItems.html',
                        controller: 'QuoteLineItemController'
                    }
                },
                resolve: {
                }
            })
            .state('quoteLineItem.detail', {
                parent: 'entity',
                url: '/quoteLineItem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'QuoteLineItem'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quoteLineItem/quoteLineItem-detail.html',
                        controller: 'QuoteLineItemDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'QuoteLineItem', function($stateParams, QuoteLineItem) {
                        return QuoteLineItem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('quoteLineItem.new', {
                parent: 'quoteLineItem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quoteLineItem/quoteLineItem-dialog.html',
                        controller: 'QuoteLineItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    cost: null,
                                    soldFor: null,
                                    qtyQuoted: null,
                                    discountPriceGroupCode: null,
                                    totalTaxCharge: null,
                                    discountPercentage: null,
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
                        $state.go('quoteLineItem', null, { reload: true });
                    }, function() {
                        $state.go('quoteLineItem');
                    })
                }]
            })
            .state('quoteLineItem.edit', {
                parent: 'quoteLineItem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quoteLineItem/quoteLineItem-dialog.html',
                        controller: 'QuoteLineItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['QuoteLineItem', function(QuoteLineItem) {
                                return QuoteLineItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('quoteLineItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('quoteLineItem.delete', {
                parent: 'quoteLineItem',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quoteLineItem/quoteLineItem-delete-dialog.html',
                        controller: 'QuoteLineItemDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['QuoteLineItem', function(QuoteLineItem) {
                                return QuoteLineItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('quoteLineItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
