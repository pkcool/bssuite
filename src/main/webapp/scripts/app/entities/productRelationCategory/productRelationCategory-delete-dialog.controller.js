'use strict';

angular.module('bssuiteApp')
	.controller('ProductRelationCategoryDeleteController', function($scope, $modalInstance, entity, ProductRelationCategory) {

        $scope.productRelationCategory = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProductRelationCategory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });