'use strict';

angular.module('bssuiteApp')
    .factory('QuoteLineItemSearch', function ($resource) {
        return $resource('api/_search/quoteLineItems/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
