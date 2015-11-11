'use strict';

angular.module('bssuiteApp')
	.controller('CalendarItemDeleteController', function($scope, $modalInstance, entity, CalendarItem) {

        $scope.calendarItem = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CalendarItem.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });