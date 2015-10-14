'use strict';

angular.module('bssuiteApp')
    .factory('PromotionSearch', function ($resource) {
        return $resource('api/_search/promotions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
