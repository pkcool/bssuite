'use strict';

angular.module('bssuiteApp')
    .factory('StockGroupSearch', function ($resource) {
        return $resource('api/_search/stockGroups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
