'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('address', {
                parent: 'entity',
                url: '/addresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.address.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/address/addresss.html',
                        controller: 'AddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('address');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('address.detail', {
                parent: 'entity',
                url: '/address/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'bssuiteApp.address.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/address/address-detail.html',
                        controller: 'AddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('address');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Address', function($stateParams, Address) {
                        return Address.get({id : $stateParams.id});
                    }]
                }
            })
            .state('address.new', {
                parent: 'address',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/address/address-dialog.html',
                        controller: 'AddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    addressLine1: null,
                                    addressLine2: null,
                                    suburb: null,
                                    state: null,
                                    postcode: null,
                                    country: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('address');
                    })
                }]
            })
            .state('address.edit', {
                parent: 'address',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/address/address-dialog.html',
                        controller: 'AddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Address', function(Address) {
                                return Address.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
