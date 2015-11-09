"use strict";


angular.module('bssuiteApp')
.config(function ($stateProvider) {

    $stateProvider
        .state('calendar', {
            parent: 'site',
            url: '/calendar',
            views: {
                "content@": {
                    templateUrl: 'scripts/components/smart_admin/calendar/views/calendar.tpl.html'
                }
            },
            data:{
                title: 'Calendar'
            }
        });
});


