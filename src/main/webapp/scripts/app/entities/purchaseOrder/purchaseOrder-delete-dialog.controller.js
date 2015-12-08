'use strict';

angular.module('bssuiteApp')
	.controller('PurchaseOrderDeleteController', function($scope, $uibModalInstance, entity, PurchaseOrder) {

        $scope.purchaseOrder = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PurchaseOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
