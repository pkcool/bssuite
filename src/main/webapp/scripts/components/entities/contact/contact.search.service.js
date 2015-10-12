'use strict';

angular.module('bssuiteApp')
    .factory('ContactSearch', function ($resource) {
        return $resource('api/_search/contacts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
