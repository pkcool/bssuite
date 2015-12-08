'use strict';

angular.module('bssuiteApp')
	.controller('SupplierCategoryDeleteController', function($scope, $uibModalInstance, entity, SupplierCategory) {

        $scope.supplierCategory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SupplierCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
