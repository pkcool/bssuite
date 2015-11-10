'use strict';

angular.module('bssuiteApp')
    .factory('TxnActivityAudit', function ($resource, DateUtils) {
        return $resource('api/txnActivityAudits/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.editedOn = DateUtils.convertDateTimeFromServer(data.editedOn);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
