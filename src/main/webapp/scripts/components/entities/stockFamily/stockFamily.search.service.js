'use strict';

angular.module('bssuiteApp')
    .factory('StockFamilySearch', function ($resource) {
        return $resource('api/_search/stockFamilys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
