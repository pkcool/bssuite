'use strict';

angular.module('bssuiteApp')
    .factory('TxnActivityAuditSearch', function ($resource) {
        return $resource('api/_search/txnActivityAudits/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
