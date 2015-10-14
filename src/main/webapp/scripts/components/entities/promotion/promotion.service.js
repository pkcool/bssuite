'use strict';

angular.module('bssuiteApp')
    .factory('Promotion', function ($resource, DateUtils) {
        return $resource('api/promotions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startDate = DateUtils.convertLocaleDateFromServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    data.dateCreated = DateUtils.convertLocaleDateFromServer(data.dateCreated);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.dateCreated = DateUtils.convertLocaleDateToServer(data.dateCreated);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.dateCreated = DateUtils.convertLocaleDateToServer(data.dateCreated);
                    return angular.toJson(data);
                }
            }
        });
    });
