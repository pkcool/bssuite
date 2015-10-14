'use strict';

angular.module('bssuiteApp')
    .factory('CarrierSearch', function ($resource) {
        return $resource('api/_search/carriers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
