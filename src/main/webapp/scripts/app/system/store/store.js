'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('store_management', {
                parent: 'system',
                url: '/store_management',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Store management'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/system/store/stores.html',
                        controller: 'StoreManagementController'
                    }
                },
                resolve: {
                }
            })
            .state('store_management.new', {
                parent: 'store_management',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/system/store/store-dialog.html',
                        controller: 'StoreManagementDialogController',
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
                            $state.go('store_management', null, { reload: true });
                        }, function() {
                            $state.go('store_management');
                        })
                }]
            })
            .state('store_management.edit', {
                parent: 'system',
                url: '/store_management/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Edit store'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/system/store/store-edit.html',
                        controller: 'StoreManagementEditController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Store', function($stateParams, Store) {
                        return Store.get({id : $stateParams.id});
                    }]
                }

            });
    });
