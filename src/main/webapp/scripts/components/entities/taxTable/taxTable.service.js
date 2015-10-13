'use strict';

angular.module('bssuiteApp')
    .factory('TaxTable', function ($resource, DateUtils) {
        return $resource('api/taxTables/:id', {}, {
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
