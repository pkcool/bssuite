'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('taxTable', {
                parent: 'entity',
                url: '/taxTables',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.taxTable.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/taxTable/taxTables.html',
                        controller: 'TaxTableController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('taxTable');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('taxTable.detail', {
                parent: 'entity',
                url: '/taxTable/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.taxTable.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/taxTable/taxTable-detail.html',
                        controller: 'TaxTableDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('taxTable');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TaxTable', function($stateParams, TaxTable) {
                        return TaxTable.get({id : $stateParams.id});
                    }]
                }
            })
            .state('taxTable.new', {
                parent: 'taxTable',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/taxTable/taxTable-dialog.html',
                        controller: 'TaxTableDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    formula: null,
                                    isAddedToTotal: null,
                                    isSubtractedFromTotal: null,
                                    isExcludedOnReporting: null,
                                    taxGroup: null,
                                    taxBase: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('taxTable', null, { reload: true });
                    }, function() {
                        $state.go('taxTable');
                    })
                }]
            })
            .state('taxTable.edit', {
                parent: 'taxTable',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/taxTable/taxTable-dialog.html',
                        controller: 'TaxTableDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TaxTable', function(TaxTable) {
                                return TaxTable.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('taxTable', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
