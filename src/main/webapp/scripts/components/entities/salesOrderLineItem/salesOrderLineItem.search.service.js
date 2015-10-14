'use strict';

angular.module('bssuiteApp')
    .factory('SalesOrderLineItemSearch', function ($resource) {
        return $resource('api/_search/salesOrderLineItems/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
