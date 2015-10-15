'use strict';

angular.module('bssuiteApp')
    .factory('BackOrderLineItemSearch', function ($resource) {
        return $resource('api/_search/backOrderLineItems/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
