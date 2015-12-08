'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bookmark', {
                parent: 'entity',
                url: '/bookmarks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bookmarks'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bookmark/bookmarks.html',
                        controller: 'BookmarkController'
                    }
                },
                resolve: {
                }
            })
            .state('bookmark.detail', {
                parent: 'entity',
                url: '/bookmark/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bookmark'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bookmark/bookmark-detail.html',
                        controller: 'BookmarkDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Bookmark', function($stateParams, Bookmark) {
                        return Bookmark.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bookmark.new', {
                parent: 'bookmark',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bookmark/bookmark-dialog.html',
                        controller: 'BookmarkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    createdOn: null,
                                    txnNumber: null,
                                    bookmarkType: null,
                                    bookmarkArea: null,
                                    keyCode: null,
                                    title: null,
                                    lastEditedOn: null,
                                    openCount: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bookmark', null, { reload: true });
                    }, function() {
                        $state.go('bookmark');
                    })
                }]
            })
            .state('bookmark.edit', {
                parent: 'bookmark',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bookmark/bookmark-dialog.html',
                        controller: 'BookmarkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Bookmark', function(Bookmark) {
                                return Bookmark.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bookmark', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bookmark.delete', {
                parent: 'bookmark',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bookmark/bookmark-delete-dialog.html',
                        controller: 'BookmarkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Bookmark', function(Bookmark) {
                                return Bookmark.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bookmark', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
