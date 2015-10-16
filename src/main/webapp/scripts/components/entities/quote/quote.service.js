'use strict';

angular.module('bssuiteApp')
    .factory('Quote', function ($resource, DateUtils) {
        return $resource('api/quotes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.quoteDate = DateUtils.convertDateTimeFromServer(data.quoteDate);
                    data.expiryDate = DateUtils.convertLocaleDateFromServer(data.expiryDate);
                    data.followupDate = DateUtils.convertLocaleDateFromServer(data.followupDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.expiryDate = DateUtils.convertLocaleDateToServer(data.expiryDate);
                    data.followupDate = DateUtils.convertLocaleDateToServer(data.followupDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.expiryDate = DateUtils.convertLocaleDateToServer(data.expiryDate);
                    data.followupDate = DateUtils.convertLocaleDateToServer(data.followupDate);
                    return angular.toJson(data);
                }
            }
        });
    });
