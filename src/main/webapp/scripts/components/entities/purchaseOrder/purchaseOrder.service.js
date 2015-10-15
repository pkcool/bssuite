'use strict';

angular.module('bssuiteApp')
    .factory('PurchaseOrder', function ($resource, DateUtils) {
        return $resource('api/purchaseOrders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                    data.expectedDeliveryDate = DateUtils.convertLocaleDateFromServer(data.expectedDeliveryDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.expectedDeliveryDate = DateUtils.convertLocaleDateToServer(data.expectedDeliveryDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.expectedDeliveryDate = DateUtils.convertLocaleDateToServer(data.expectedDeliveryDate);
                    return angular.toJson(data);
                }
            }
        });
    });
