'use strict';

angular.module('bssuiteApp')
    .factory('Carrier', function ($resource, DateUtils) {
        return $resource('api/carriers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
