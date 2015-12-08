'use strict';

angular.module('bssuiteApp')
	.controller('CalendarItemDeleteController', function($scope, $uibModalInstance, entity, CalendarItem) {

        $scope.calendarItem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CalendarItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
