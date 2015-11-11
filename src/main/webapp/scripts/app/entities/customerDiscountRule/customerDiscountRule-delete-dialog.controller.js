'use strict';

angular.module('bssuiteApp')
	.controller('CustomerDiscountRuleDeleteController', function($scope, $modalInstance, entity, CustomerDiscountRule) {

        $scope.customerDiscountRule = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CustomerDiscountRule.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });