'use strict';

angular.module('bssuiteApp')
    .factory('StockFamily', function ($resource, DateUtils) {
        return $resource('api/stockFamilys/:id', {}, {
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
