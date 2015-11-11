'use strict';

angular.module('bssuiteApp')
	.controller('PurchaseOrderDeleteController', function($scope, $modalInstance, entity, PurchaseOrder) {

        $scope.purchaseOrder = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PurchaseOrder.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });