'use strict';

angular.module('bssuiteApp')
    .factory('CalendarItemSearch', function ($resource) {
        return $resource('api/_search/calendarItems/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
