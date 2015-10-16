'use strict';

angular.module('bssuiteApp')
    .factory('InvoiceSearch', function ($resource) {
        return $resource('api/_search/invoices/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
