'use strict';

angular.module('bssuiteApp')
    .factory('Store', function ($resource, DateUtils) {
        return $resource('api/stores/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.inBusinessSince = DateUtils.convertLocaleDateFromServer(data.inBusinessSince);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.inBusinessSince = DateUtils.convertLocaleDateToServer(data.inBusinessSince);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.inBusinessSince = DateUtils.convertLocaleDateToServer(data.inBusinessSince);
                    return angular.toJson(data);
                }
            }
        });
    });
