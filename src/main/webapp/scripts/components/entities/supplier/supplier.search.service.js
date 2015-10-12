'use strict';

angular.module('bssuiteApp')
    .factory('SupplierSearch', function ($resource) {
        return $resource('api/_search/suppliers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
