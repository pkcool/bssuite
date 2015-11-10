'use strict';

angular.module('bssuiteApp')
    .factory('BookmarkSearch', function ($resource) {
        return $resource('api/_search/bookmarks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
