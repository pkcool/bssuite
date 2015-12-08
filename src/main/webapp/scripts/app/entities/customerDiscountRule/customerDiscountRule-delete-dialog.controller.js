'use strict';

angular.module('bssuiteApp')
	.controller('CustomerDiscountRuleDeleteController', function($scope, $uibModalInstance, entity, CustomerDiscountRule) {

        $scope.customerDiscountRule = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CustomerDiscountRule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
