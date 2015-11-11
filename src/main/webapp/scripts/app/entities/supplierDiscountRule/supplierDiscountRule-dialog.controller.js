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

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:supplierDiscountRuleUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.supplierDiscountRule.id != null) {
                SupplierDiscountRule.update($scope.supplierDiscountRule, onSaveSuccess, onSaveError);
            } else {
                SupplierDiscountRule.save($scope.supplierDiscountRule, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
