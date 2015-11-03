'use strict';

angular.module('bssuiteApp').controller('ProductRelationCategoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ProductRelationCategory',
        function($scope, $stateParams, $modalInstance, entity, ProductRelationCategory) {

        $scope.productRelationCategory = entity;
        $scope.load = function(id) {
            ProductRelationCategory.get({id : id}, function(result) {
                $scope.productRelationCategory = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:productRelationCategoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.productRelationCategory.id != null) {
                ProductRelationCategory.update($scope.productRelationCategory, onSaveFinished);
            } else {
                ProductRelationCategory.save($scope.productRelationCategory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
