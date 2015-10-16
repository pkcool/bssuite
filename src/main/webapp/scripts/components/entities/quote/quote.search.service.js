'use strict';

angular.module('bssuiteApp')
    .factory('QuoteSearch', function ($resource) {
        return $resource('api/_search/quotes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
