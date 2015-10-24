'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stockGroup', {
                parent: 'entity',
                url: '/stockGroups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.stockGroup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockGroup/stockGroups.html',
                        controller: 'StockGroupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stockGroup.detail', {
                parent: 'entity',
                url: '/stockGroup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.stockGroup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockGroup/stockGroup-detail.html',
                        controller: 'StockGroupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StockGroup', function($stateParams, StockGroup) {
                        return StockGroup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('stockGroup.new', {
                parent: 'stockGroup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockGroup/stockGroup-dialog.html',
                        controller: 'StockGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    isDiminishing: null,
                                    lowestMargin: null,
                                    highestMargin: null,
                                    isDiscountAllowed: null,
                                    comment: null,
                                    isArchived: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('stockGroup', null, { reload: true });
                    }, function() {
                        $state.go('stockGroup');
                    })
                }]
            })
            .state('stockGroup.edit', {
                parent: 'stockGroup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockGroup/stockGroup-dialog.html',
                        controller: 'StockGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StockGroup', function(StockGroup) {
                                return StockGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stockGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
