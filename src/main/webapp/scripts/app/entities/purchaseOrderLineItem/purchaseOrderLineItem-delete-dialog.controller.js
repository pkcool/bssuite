'use strict';

angular.module('bssuiteApp')
	.controller('PurchaseOrderLineItemDeleteController', function($scope, $modalInstance, entity, PurchaseOrderLineItem) {

        $scope.purchaseOrderLineItem = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PurchaseOrderLineItem.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });