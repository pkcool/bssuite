'use strict';

angular.module('bssuiteApp')
    .factory('CustomerSearch', function ($resource) {
        return $resource('api/_search/customers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
