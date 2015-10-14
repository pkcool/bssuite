'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('carrier', {
                parent: 'entity',
                url: '/carriers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Carriers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/carrier/carriers.html',
                        controller: 'CarrierController'
                    }
                },
                resolve: {
                }
            })
            .state('carrier.detail', {
                parent: 'entity',
                url: '/carrier/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Carrier'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/carrier/carrier-detail.html',
                        controller: 'CarrierDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Carrier', function($stateParams, Carrier) {
                        return Carrier.get({id : $stateParams.id});
                    }]
                }
            })
            .state('carrier.new', {
                parent: 'carrier',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/carrier/carrier-dialog.html',
                        controller: 'CarrierDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    phone: null,
                                    mobile: null,
                                    accountNo: null,
                                    comment: null,
                                    isAvailableOnMonday: null,
                                    isAvailableOnTuesday: null,
                                    isAvailableOnWednesday: null,
                                    isAvailableOnThursday: null,
                                    isAvailableOnFriday: null,
                                    isAvailableOnSaturday: null,
                                    isAvailableOnSunday: null,
                                    docketRefNo: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('carrier', null, { reload: true });
                    }, function() {
                        $state.go('carrier');
                    })
                }]
            })
            .state('carrier.edit', {
                parent: 'carrier',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/carrier/carrier-dialog.html',
                        controller: 'CarrierDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Carrier', function(Carrier) {
                                return Carrier.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('carrier', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
