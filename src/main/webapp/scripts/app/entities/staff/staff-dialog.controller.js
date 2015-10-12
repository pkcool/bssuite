'use strict';

angular.module('bssuiteApp').controller('StaffDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Staff', 'Store',
        function($scope, $stateParams, $modalInstance, entity, Staff, Store) {

        $scope.staff = entity;
        $scope.stores = Store.query();
        $scope.load = function(id) {
            Staff.get({id : id}, function(result) {
                $scope.staff = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:staffUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.staff.id != null) {
                Staff.update($scope.staff, onSaveFinished);
            } else {
                Staff.save($scope.staff, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
