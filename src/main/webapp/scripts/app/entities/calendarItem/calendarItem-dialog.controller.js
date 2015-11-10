'use strict';

angular.module('bssuiteApp').controller('CalendarItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CalendarItem', 'Staff',
        function($scope, $stateParams, $modalInstance, entity, CalendarItem, Staff) {

        $scope.calendarItem = entity;
        $scope.staffs = Staff.query();
        $scope.load = function(id) {
            CalendarItem.get({id : id}, function(result) {
                $scope.calendarItem = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:calendarItemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.calendarItem.id != null) {
                CalendarItem.update($scope.calendarItem, onSaveFinished);
            } else {
                CalendarItem.save($scope.calendarItem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
