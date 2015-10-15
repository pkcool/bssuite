'use strict';

angular.module('bssuiteApp')
    .factory('BackOrderLineItem', function ($resource, DateUtils) {
        return $resource('api/backOrderLineItems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.allocatedDate = DateUtils.convertDateTimeFromServer(data.allocatedDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
