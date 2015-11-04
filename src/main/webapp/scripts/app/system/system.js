'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('system', {
                abstract: true,
                parent: 'site'
            });
    });
