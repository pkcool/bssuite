'use strict';

angular.module('bssuiteApp')
    .factory('Bookmark', function ($resource, DateUtils) {
        return $resource('api/bookmarks/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                    data.lastEditedOn = DateUtils.convertDateTimeFromServer(data.lastEditedOn);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
