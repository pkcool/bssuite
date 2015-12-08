'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('staff', {
                parent: 'entity',
                url: '/staffs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Staffs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/staff/staffs.html',
                        controller: 'StaffController'
                    }
                },
                resolve: {
                }
            })
            .state('staff.detail', {
                parent: 'entity',
                url: '/staff/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Staff'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/staff/staff-detail.html',
                        controller: 'StaffDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Staff', function($stateParams, Staff) {
                        return Staff.get({id : $stateParams.id});
                    }]
                }
            })
            .state('staff.new', {
                parent: 'staff',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/staff/staff-dialog.html',
                        controller: 'StaffDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    comment: null,
                                    commission: null,
                                    occupation: null,
                                    workPhone: null,
                                    homePhone: null,
                                    workMobile: null,
                                    homeMobile: null,
                                    webEmail: null,
                                    homeEmail: null,
                                    birthday: null,
                                    isLockedToStore: null,
                                    isTechnical: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('staff', null, { reload: true });
                    }, function() {
                        $state.go('staff');
                    })
                }]
            })
            .state('staff.edit', {
                parent: 'staff',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/staff/staff-dialog.html',
                        controller: 'StaffDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Staff', function(Staff) {
                                return Staff.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('staff', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('staff.delete', {
                parent: 'staff',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/staff/staff-delete-dialog.html',
                        controller: 'StaffDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Staff', function(Staff) {
                                return Staff.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('staff', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
