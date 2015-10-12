'use strict';

angular.module('bssuiteApp')
    .factory('Staff', function ($resource, DateUtils) {
        return $resource('api/staffs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.birthday = DateUtils.convertLocaleDateFromServer(data.birthday);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.birthday = DateUtils.convertLocaleDateToServer(data.birthday);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.birthday = DateUtils.convertLocaleDateToServer(data.birthday);
                    return angular.toJson(data);
                }
            }
        });
    });
