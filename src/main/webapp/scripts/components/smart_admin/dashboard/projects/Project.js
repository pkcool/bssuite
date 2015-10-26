"use strict";

angular.module('bssuiteApp').factory('Project', function($http, APP_CONFIG){
    return {
        list: $http.get(APP_CONFIG.apiRootUrl + '/projects.json')
    }
});
