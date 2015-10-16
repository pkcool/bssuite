'use strict';

angular.module('bssuiteApp')
    .factory('InvoiceLineItemSearch', function ($resource) {
        return $resource('api/_search/invoiceLineItems/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
