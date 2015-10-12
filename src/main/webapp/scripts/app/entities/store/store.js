'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('store', {
                parent: 'entity',
                url: '/stores',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Stores'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/store/stores.html',
                        controller: 'StoreController'
                    }
                },
                resolve: {
                }
            })
            .state('store.detail', {
                parent: 'entity',
                url: '/store/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Store'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/store/store-detail.html',
                        controller: 'StoreDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Store', function($stateParams, Store) {
                        return Store.get({id : $stateParams.id});
                    }]
                }
            })
            .state('store.new', {
                parent: 'store',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/store/store-dialog.html',
                        controller: 'StoreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    address1: null,
                                    address2: null,
                                    suburb: null,
                                    state: null,
                                    postcode: null,
                                    country: null,
                                    phone: null,
                                    fax: null,
                                    email: null,
                                    webUrl: null,
                                    inBisinessSince: null,
                                    isArchived: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('store', null, { reload: true });
                    }, function() {
                        $state.go('store');
                    })
                }]
            })
            .state('store.edit', {
                parent: 'store',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/store/store-dialog.html',
                        controller: 'StoreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Store', function(Store) {
                                return Store.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('store', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
