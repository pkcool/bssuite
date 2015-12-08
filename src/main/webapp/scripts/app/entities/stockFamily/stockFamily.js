'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stockFamily', {
                parent: 'entity',
                url: '/stockFamilys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'StockFamilys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockFamily/stockFamilys.html',
                        controller: 'StockFamilyController'
                    }
                },
                resolve: {
                }
            })
            .state('stockFamily.detail', {
                parent: 'entity',
                url: '/stockFamily/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'StockFamily'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockFamily/stockFamily-detail.html',
                        controller: 'StockFamilyDetailController'
                    }
                },
                resolve: {
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
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
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
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
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
            })
            .state('stockFamily.delete', {
                parent: 'stockFamily',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/stockFamily/stockFamily-delete-dialog.html',
                        controller: 'StockFamilyDeleteController',
                        size: 'md',
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
