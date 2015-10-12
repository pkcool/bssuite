'use strict';

angular.module('bssuiteApp')
    .factory('SupplierCategorySearch', function ($resource) {
        return $resource('api/_search/supplierCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
