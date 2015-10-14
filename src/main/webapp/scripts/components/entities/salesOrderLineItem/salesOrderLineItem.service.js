'use strict';

angular.module('bssuiteApp')
    .factory('SalesOrderLineItem', function ($resource, DateUtils) {
        return $resource('api/salesOrderLineItems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.requiredDate = DateUtils.convertLocaleDateFromServer(data.requiredDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.requiredDate = DateUtils.convertLocaleDateToServer(data.requiredDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.requiredDate = DateUtils.convertLocaleDateToServer(data.requiredDate);
                    return angular.toJson(data);
                }
            }
        });
    });
