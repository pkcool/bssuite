'use strict';

angular.module('bssuiteApp')
    .controller('CalendarItemDetailController', function ($scope, $rootScope, $stateParams, entity, CalendarItem, Staff) {
        $scope.calendarItem = entity;
        $scope.load = function (id) {
            CalendarItem.get({id: id}, function(result) {
                $scope.calendarItem = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:calendarItemUpdate', function(event, result) {
            $scope.calendarItem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
