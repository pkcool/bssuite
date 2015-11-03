'use strict';

angular.module('bssuiteApp')
    .factory('RelatedProductSearch', function ($resource) {
        return $resource('api/_search/relatedProducts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
