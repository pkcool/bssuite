'use strict';

angular.module('bssuiteApp').controller('SupplierCategoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SupplierCategory',
        function($scope, $stateParams, $modalInstance, entity, SupplierCategory) {

        $scope.supplierCategory = entity;
        $scope.load = function(id) {
            SupplierCategory.get({id : id}, function(result) {
                $scope.supplierCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:supplierCategoryUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.supplierCategory.id != null) {
                SupplierCategory.update($scope.supplierCategory, onSaveSuccess, onSaveError);
            } else {
                SupplierCategory.save($scope.supplierCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
