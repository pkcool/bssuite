'use strict';

angular.module('bssuiteApp')
	.controller('PurchaseOrderLineItemDeleteController', function($scope, $uibModalInstance, entity, PurchaseOrderLineItem) {

        $scope.purchaseOrderLineItem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PurchaseOrderLineItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
