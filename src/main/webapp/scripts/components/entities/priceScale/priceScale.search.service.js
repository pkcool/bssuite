'use strict';

angular.module('bssuiteApp')
    .factory('PriceScaleSearch', function ($resource) {
        return $resource('api/_search/priceScales/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
