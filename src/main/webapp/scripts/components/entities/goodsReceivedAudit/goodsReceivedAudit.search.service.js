'use strict';

angular.module('bssuiteApp')
    .factory('GoodsReceivedAuditSearch', function ($resource) {
        return $resource('api/_search/goodsReceivedAudits/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
