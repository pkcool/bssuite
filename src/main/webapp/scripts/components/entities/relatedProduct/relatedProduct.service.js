'use strict';

angular.module('bssuiteApp')
    .factory('RelatedProduct', function ($resource, DateUtils) {
        return $resource('api/relatedProducts/:id', {}, {
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
