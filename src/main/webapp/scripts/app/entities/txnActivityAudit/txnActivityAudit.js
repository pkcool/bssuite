'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('txnActivityAudit', {
                parent: 'entity',
                url: '/txnActivityAudits',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TxnActivityAudits'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/txnActivityAudit/txnActivityAudits.html',
                        controller: 'TxnActivityAuditController'
                    }
                },
                resolve: {
                }
            })
            .state('txnActivityAudit.detail', {
                parent: 'entity',
                url: '/txnActivityAudit/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TxnActivityAudit'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/txnActivityAudit/txnActivityAudit-detail.html',
                        controller: 'TxnActivityAuditDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TxnActivityAudit', function($stateParams, TxnActivityAudit) {
                        return TxnActivityAudit.get({id : $stateParams.id});
                    }]
                }
            })
            .state('txnActivityAudit.new', {
                parent: 'txnActivityAudit',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/txnActivityAudit/txnActivityAudit-dialog.html',
                        controller: 'TxnActivityAuditDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    editedOn: null,
                                    txnNumber: null,
                                    txnType: null,
                                    txnAmount: null,
                                    bankAcc: null,
                                    editType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('txnActivityAudit', null, { reload: true });
                    }, function() {
                        $state.go('txnActivityAudit');
                    })
                }]
            })
            .state('txnActivityAudit.edit', {
                parent: 'txnActivityAudit',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/txnActivityAudit/txnActivityAudit-dialog.html',
                        controller: 'TxnActivityAuditDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TxnActivityAudit', function(TxnActivityAudit) {
                                return TxnActivityAudit.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('txnActivityAudit', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
