'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('priceScale', {
                parent: 'entity',
                url: '/priceScales',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.priceScale.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/priceScale/priceScales.html',
                        controller: 'PriceScaleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('priceScale');
                        $translatePartialLoader.addPart('roundingMethod');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('priceScale.detail', {
                parent: 'entity',
                url: '/priceScale/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.priceScale.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/priceScale/priceScale-detail.html',
                        controller: 'PriceScaleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('priceScale');
                        $translatePartialLoader.addPart('roundingMethod');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PriceScale', function($stateParams, PriceScale) {
                        return PriceScale.get({id : $stateParams.id});
                    }]
                }
            })
            .state('priceScale.new', {
                parent: 'priceScale',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/priceScale/priceScale-dialog.html',
                        controller: 'PriceScaleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    price: null,
                                    value: null,
                                    round: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('priceScale', null, { reload: true });
                    }, function() {
                        $state.go('priceScale');
                    })
                }]
            })
            .state('priceScale.edit', {
                parent: 'priceScale',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/priceScale/priceScale-dialog.html',
                        controller: 'PriceScaleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PriceScale', function(PriceScale) {
                                return PriceScale.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('priceScale', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
