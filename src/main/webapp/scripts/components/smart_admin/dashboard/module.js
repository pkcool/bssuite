'use strict';

angular.module('bssuiteApp')

.config(function ($stateProvider) {
    $stateProvider
        .state('dashboard', {
            parent: 'site',
            url: '/dashboard',
            views: {
                "content@": {
                    controller: 'DashboardCtrl',
                    templateUrl: 'scripts/components/smart_admin/dashboard/dashboard.html'
                }
            },
            data:{
                title: 'Dashboard'
            }
        });
});
