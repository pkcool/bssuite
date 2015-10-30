"use strict";

angular.module('bssuiteApp').directive('languageSelector', function(Language){
    return {
        restrict: "EA",
        replace: true,
        templateUrl: "scripts/components/smart_admin/layout/language/language-selector.tpl.html",
        scope: true
    }
});
