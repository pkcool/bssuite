'use strict';

angular.module('bssuiteApp')
    .factory('StaffSearch', function ($resource) {
        return $resource('api/_search/staffs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
