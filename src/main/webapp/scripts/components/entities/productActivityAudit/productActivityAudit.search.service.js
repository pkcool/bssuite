'use strict';

angular.module('bssuiteApp')
    .factory('ProductActivityAuditSearch', function ($resource) {
        return $resource('api/_search/productActivityAudits/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
