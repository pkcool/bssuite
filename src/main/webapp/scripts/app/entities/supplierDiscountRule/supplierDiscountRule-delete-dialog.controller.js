'use strict';

angular.module('bssuiteApp')
	.controller('SupplierDiscountRuleDeleteController', function($scope, $uibModalInstance, entity, SupplierDiscountRule) {

        $scope.supplierDiscountRule = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SupplierDiscountRule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
