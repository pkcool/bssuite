'use strict';

angular.module('bssuiteApp').controller('SupplierDiscountRuleDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SupplierDiscountRule',
        function($scope, $stateParams, $modalInstance, entity, SupplierDiscountRule) {

        $scope.supplierDiscountRule = entity;
        $scope.load = function(id) {
            SupplierDiscountRule.get({id : id}, function(result) {
                $scope.supplierDiscountRule = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:supplierDiscountRuleUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.supplierDiscountRule.id != null) {
                SupplierDiscountRule.update($scope.supplierDiscountRule, onSaveFinished);
            } else {
                SupplierDiscountRule.save($scope.supplierDiscountRule, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
