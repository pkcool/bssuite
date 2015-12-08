'use strict';

angular.module('bssuiteApp').controller('ProductRelationCategoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductRelationCategory',
        function($scope, $stateParams, $uibModalInstance, entity, ProductRelationCategory) {

        $scope.productRelationCategory = entity;
        $scope.load = function(id) {
            ProductRelationCategory.get({id : id}, function(result) {
                $scope.productRelationCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:productRelationCategoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.productRelationCategory.id != null) {
                ProductRelationCategory.update($scope.productRelationCategory, onSaveSuccess, onSaveError);
            } else {
                ProductRelationCategory.save($scope.productRelationCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
