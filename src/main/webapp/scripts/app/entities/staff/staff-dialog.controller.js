'use strict';

angular.module('bssuiteApp').controller('StaffDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Staff', 'Store',
        function($scope, $stateParams, $uibModalInstance, entity, Staff, Store) {

        $scope.staff = entity;
        $scope.stores = Store.query();
        $scope.load = function(id) {
            Staff.get({id : id}, function(result) {
                $scope.staff = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:staffUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.staff.id != null) {
                Staff.update($scope.staff, onSaveSuccess, onSaveError);
            } else {
                Staff.save($scope.staff, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
