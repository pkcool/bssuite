'use strict';

angular.module('bssuiteApp')
	.controller('SupplierDeleteController', function($scope, $modalInstance, entity, Supplier) {

        $scope.supplier = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Supplier.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });