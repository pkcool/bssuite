'use strict';

angular.module('bssuiteApp')
	.controller('SupplierDiscountRuleDeleteController', function($scope, $modalInstance, entity, SupplierDiscountRule) {

        $scope.supplierDiscountRule = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SupplierDiscountRule.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });