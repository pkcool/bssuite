'use strict';

angular.module('bssuiteApp')
    .factory('GoodsReceivedAudit', function ($resource, DateUtils) {
        return $resource('api/goodsReceivedAudits/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.receivedOn = DateUtils.convertDateTimeFromServer(data.receivedOn);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
