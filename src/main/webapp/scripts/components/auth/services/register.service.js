'use strict';

angular.module('bssuiteApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


