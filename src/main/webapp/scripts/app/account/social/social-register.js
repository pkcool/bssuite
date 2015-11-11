'use strict';

angular.module('bssuiteApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('social-register', {
                parent: 'account',
                url: '/social-register/:provider?{success:boolean}',
                data: {
                    authorities: [],
                    pageTitle: 'Register with {{ label }}'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/social/social-register.html',
                        controller: 'SocialRegisterController'
                    }
                },
                resolve: {
                    
                }
            });
    });
