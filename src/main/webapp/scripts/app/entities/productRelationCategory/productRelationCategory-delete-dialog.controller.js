'use strict';

angular.module('bssuiteApp')
	.controller('ProductRelationCategoryDeleteController', function($scope, $uibModalInstance, entity, ProductRelationCategory) {

        $scope.productRelationCategory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProductRelationCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
