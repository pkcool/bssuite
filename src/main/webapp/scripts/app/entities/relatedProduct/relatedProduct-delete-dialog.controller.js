'use strict';

angular.module('bssuiteApp')
	.controller('RelatedProductDeleteController', function($scope, $modalInstance, entity, RelatedProduct) {

        $scope.relatedProduct = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RelatedProduct.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });