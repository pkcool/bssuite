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
                parent: 'system',
                url: '/store_management/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'New Store'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/system/store/store-edit.html',
                        controller: 'StoreManagementEditController'
                    }
                },
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
                            inBusinessSince: null,
                            isArchived: null,
                            id: null
                        };
                    }
                }
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
