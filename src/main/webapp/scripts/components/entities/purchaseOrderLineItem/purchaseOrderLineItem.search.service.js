'use strict';

angular.module('bssuiteApp')
    .factory('PurchaseOrderLineItemSearch', function ($resource) {
        return $resource('api/_search/purchaseOrderLineItems/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
