'use strict';

angular.module('bssuiteApp')
	.controller('RelatedProductDeleteController', function($scope, $uibModalInstance, entity, RelatedProduct) {

        $scope.relatedProduct = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RelatedProduct.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
