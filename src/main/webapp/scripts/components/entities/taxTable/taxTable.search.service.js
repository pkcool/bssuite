'use strict';

angular.module('bssuiteApp')
    .factory('TaxTableSearch', function ($resource) {
        return $resource('api/_search/taxTables/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
