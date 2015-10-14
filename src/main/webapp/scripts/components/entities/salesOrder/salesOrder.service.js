'use strict';

angular.module('bssuiteApp')
    .factory('SalesOrder', function ($resource, DateUtils) {
        return $resource('api/salesOrders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.txnDate = DateUtils.convertDateTimeFromServer(data.txnDate);
                    data.forwardDate = DateUtils.convertLocaleDateFromServer(data.forwardDate);
                    data.requiredDate = DateUtils.convertLocaleDateFromServer(data.requiredDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.forwardDate = DateUtils.convertLocaleDateToServer(data.forwardDate);
                    data.requiredDate = DateUtils.convertLocaleDateToServer(data.requiredDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.forwardDate = DateUtils.convertLocaleDateToServer(data.forwardDate);
                    data.requiredDate = DateUtils.convertLocaleDateToServer(data.requiredDate);
                    return angular.toJson(data);
                }
            }
        });
    });
