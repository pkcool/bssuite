'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('calendarItem', {
                parent: 'entity',
                url: '/calendarItems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CalendarItems'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/calendarItem/calendarItems.html',
                        controller: 'CalendarItemController'
                    }
                },
                resolve: {
                }
            })
            .state('calendarItem.detail', {
                parent: 'entity',
                url: '/calendarItem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CalendarItem'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/calendarItem/calendarItem-detail.html',
                        controller: 'CalendarItemDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'CalendarItem', function($stateParams, CalendarItem) {
                        return CalendarItem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('calendarItem.new', {
                parent: 'calendarItem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/calendarItem/calendarItem-dialog.html',
                        controller: 'CalendarItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    startedOn: null,
                                    endedOn: null,
                                    isAllDayEvent: null,
                                    isRemainderEnabled: null,
                                    remainderTime: null,
                                    webUrl: null,
                                    isEditable: null,
                                    alarmType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('calendarItem', null, { reload: true });
                    }, function() {
                        $state.go('calendarItem');
                    })
                }]
            })
            .state('calendarItem.edit', {
                parent: 'calendarItem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/calendarItem/calendarItem-dialog.html',
                        controller: 'CalendarItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CalendarItem', function(CalendarItem) {
                                return CalendarItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('calendarItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
