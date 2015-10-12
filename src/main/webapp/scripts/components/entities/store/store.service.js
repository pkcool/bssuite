'use strict';

angular.module('bssuiteApp')
    .factory('Store', function ($resource, DateUtils) {
        return $resource('api/stores/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.inBisinessSince = DateUtils.convertLocaleDateFromServer(data.inBisinessSince);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.inBisinessSince = DateUtils.convertLocaleDateToServer(data.inBisinessSince);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.inBisinessSince = DateUtils.convertLocaleDateToServer(data.inBisinessSince);
                    return angular.toJson(data);
                }
            }
        });
    });
