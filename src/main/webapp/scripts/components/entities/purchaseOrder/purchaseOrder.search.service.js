'use strict';

angular.module('bssuiteApp')
    .factory('PurchaseOrderSearch', function ($resource) {
        return $resource('api/_search/purchaseOrders/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
