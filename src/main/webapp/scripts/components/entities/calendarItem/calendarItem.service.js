'use strict';

angular.module('bssuiteApp')
    .factory('CalendarItem', function ($resource, DateUtils) {
        return $resource('api/calendarItems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startedOn = DateUtils.convertDateTimeFromServer(data.startedOn);
                    data.endedOn = DateUtils.convertDateTimeFromServer(data.endedOn);
                    data.remainderTime = DateUtils.convertDateTimeFromServer(data.remainderTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
