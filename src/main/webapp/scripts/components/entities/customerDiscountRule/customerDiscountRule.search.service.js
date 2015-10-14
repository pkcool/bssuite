'use strict';

angular.module('bssuiteApp')
    .factory('CustomerDiscountRuleSearch', function ($resource) {
        return $resource('api/_search/customerDiscountRules/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
