'use strict';

angular.module('bssuiteApp').controller('RelatedProductDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RelatedProduct', 'Product', 'ProductRelationCategory',
        function($scope, $stateParams, $uibModalInstance, entity, RelatedProduct, Product, ProductRelationCategory) {

        $scope.relatedProduct = entity;
        $scope.products = Product.query();
        $scope.productrelationcategorys = ProductRelationCategory.query();
        $scope.load = function(id) {
            RelatedProduct.get({id : id}, function(result) {
                $scope.relatedProduct = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:relatedProductUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.relatedProduct.id != null) {
                RelatedProduct.update($scope.relatedProduct, onSaveSuccess, onSaveError);
            } else {
                RelatedProduct.save($scope.relatedProduct, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
