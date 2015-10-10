 'use strict';

angular.module('bssuiteApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-bssuiteApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-bssuiteApp-params')});
                }
                return response;
            }
        };
    });
