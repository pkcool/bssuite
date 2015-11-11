'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('quote', {
                parent: 'entity',
                url: '/quotes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Quotes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quote/quotes.html',
                        controller: 'QuoteController'
                    }
                },
                resolve: {
                }
            })
            .state('quote.detail', {
                parent: 'entity',
                url: '/quote/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Quote'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quote/quote-detail.html',
                        controller: 'QuoteDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Quote', function($stateParams, Quote) {
                        return Quote.get({id : $stateParams.id});
                    }]
                }
            })
            .state('quote.new', {
                parent: 'quote',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quote/quote-dialog.html',
                        controller: 'QuoteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    quoteNo: null,
                                    status: null,
                                    quoteDate: null,
                                    expiryDate: null,
                                    followupDate: null,
                                    reference: null,
                                    ourRef: null,
                                    freight: null,
                                    reasonForLoss: null,
                                    isTaxable: null,
                                    taxExemptionCode: null,
                                    isLocked: null,
                                    adjustTax: null,
                                    adjustTaxExempt: null,
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
                        $state.go('quote', null, { reload: true });
                    }, function() {
                        $state.go('quote');
                    })
                }]
            })
            .state('quote.edit', {
                parent: 'quote',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quote/quote-dialog.html',
                        controller: 'QuoteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Quote', function(Quote) {
                                return Quote.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('quote', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('quote.delete', {
                parent: 'quote',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quote/quote-delete-dialog.html',
                        controller: 'QuoteDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Quote', function(Quote) {
                                return Quote.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('quote', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
