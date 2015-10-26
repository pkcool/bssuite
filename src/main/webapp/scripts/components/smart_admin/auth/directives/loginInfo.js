"use strict";

angular.module('bssuiteApp').directive('loginInfo', function(User){

    return {
        restrict: 'A',
        templateUrl: 'scripts/components/smart_admin/auth/directives/login-info.tpl.html',
        link: function(scope, element){
        }
    }
})
