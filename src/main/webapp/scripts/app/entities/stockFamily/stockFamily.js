'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stockFamily', {
                parent: 'entity',
                url: '/stockFamilys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.stockFamily.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockFamily/stockFamilys.html',
                        controller: 'StockFamilyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockFamily');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stockFamily.detail', {
                parent: 'entity',
                url: '/stockFamily/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.stockFamily.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockFamily/stockFamily-detail.html',
                        controller: 'StockFamilyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockFamily');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StockFamily', function($stateParams, StockFamily) {
                        return StockFamily.get({id : $stateParams.id});
                    }]
                }
            })
            .state('stockFamily.new', {
                parent: 'stockFamily',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockFamily/stockFamily-dialog.html',
                        controller: 'StockFamilyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    isDiminishing: null,
                                    lowestMargin: null,
                                    highestMargin: null,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('stockFamily', null, { reload: true });
                    }, function() {
                        $state.go('stockFamily');
                    })
                }]
            })
            .state('stockFamily.edit', {
                parent: 'stockFamily',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockFamily/stockFamily-dialog.html',
                        controller: 'StockFamilyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StockFamily', function(StockFamily) {
                                return StockFamily.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stockFamily', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
