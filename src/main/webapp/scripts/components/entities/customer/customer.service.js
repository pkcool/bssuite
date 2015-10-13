'use strict';

angular.module('bssuiteApp')
    .factory('Customer', function ($resource, DateUtils) {
        return $resource('api/customers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creditCardExpiryDate = DateUtils.convertLocaleDateFromServer(data.creditCardExpiryDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.creditCardExpiryDate = DateUtils.convertLocaleDateToServer(data.creditCardExpiryDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.creditCardExpiryDate = DateUtils.convertLocaleDateToServer(data.creditCardExpiryDate);
                    return angular.toJson(data);
                }
            }
        });
    });
