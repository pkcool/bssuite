'use strict';

angular.module('bssuiteApp')
    .factory('ProductRelationCategory', function ($resource, DateUtils) {
        return $resource('api/productRelationCategorys/:id', {}, {
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
