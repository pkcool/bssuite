'use strict';

angular.module('bssuiteApp').controller('RelatedProductDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'RelatedProduct', 'Product', 'ProductRelationCategory',
        function($scope, $stateParams, $modalInstance, entity, RelatedProduct, Product, ProductRelationCategory) {

        $scope.relatedProduct = entity;
        $scope.products = Product.query();
        $scope.productrelationcategorys = ProductRelationCategory.query();
        $scope.load = function(id) {
            RelatedProduct.get({id : id}, function(result) {
                $scope.relatedProduct = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:relatedProductUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.relatedProduct.id != null) {
                RelatedProduct.update($scope.relatedProduct, onSaveFinished);
            } else {
                RelatedProduct.save($scope.relatedProduct, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
