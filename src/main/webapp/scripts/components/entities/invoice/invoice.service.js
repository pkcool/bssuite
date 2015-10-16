'use strict';

angular.module('bssuiteApp')
    .factory('Invoice', function ($resource, DateUtils) {
        return $resource('api/invoices/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.txnDate = DateUtils.convertDateTimeFromServer(data.txnDate);
                    data.dueDate = DateUtils.convertLocaleDateFromServer(data.dueDate);
                    data.dateOfDeposit = DateUtils.convertLocaleDateFromServer(data.dateOfDeposit);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dueDate = DateUtils.convertLocaleDateToServer(data.dueDate);
                    data.dateOfDeposit = DateUtils.convertLocaleDateToServer(data.dateOfDeposit);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dueDate = DateUtils.convertLocaleDateToServer(data.dueDate);
                    data.dateOfDeposit = DateUtils.convertLocaleDateToServer(data.dateOfDeposit);
                    return angular.toJson(data);
                }
            }
        });
    });
