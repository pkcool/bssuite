'use strict';

angular.module('bssuiteApp')
    .factory('SupplierDiscountRuleSearch', function ($resource) {
        return $resource('api/_search/supplierDiscountRules/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
