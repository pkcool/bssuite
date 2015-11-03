'use strict';

angular.module('bssuiteApp')
    .factory('ProductRelationCategorySearch', function ($resource) {
        return $resource('api/_search/productRelationCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
