'use strict';

angular.module('bssuiteApp')
    .factory('SalesOrderSearch', function ($resource) {
        return $resource('api/_search/salesOrders/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
