'use strict';

angular.module('bssuiteApp')
    .factory('CustomerCategorySearch', function ($resource) {
        return $resource('api/_search/customerCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
