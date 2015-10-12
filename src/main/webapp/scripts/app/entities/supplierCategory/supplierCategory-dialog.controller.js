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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:supplierCategoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.supplierCategory.id != null) {
                SupplierCategory.update($scope.supplierCategory, onSaveFinished);
            } else {
                SupplierCategory.save($scope.supplierCategory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
