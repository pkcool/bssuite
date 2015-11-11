'use strict';

angular.module('bssuiteApp')
	.controller('SupplierCategoryDeleteController', function($scope, $modalInstance, entity, SupplierCategory) {

        $scope.supplierCategory = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SupplierCategory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });