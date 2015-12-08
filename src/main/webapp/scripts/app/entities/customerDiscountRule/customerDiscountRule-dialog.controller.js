'use strict';

angular.module('bssuiteApp').controller('CustomerDiscountRuleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerDiscountRule',
        function($scope, $stateParams, $uibModalInstance, entity, CustomerDiscountRule) {

        $scope.customerDiscountRule = entity;
        $scope.load = function(id) {
            CustomerDiscountRule.get({id : id}, function(result) {
                $scope.customerDiscountRule = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:customerDiscountRuleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.customerDiscountRule.id != null) {
                CustomerDiscountRule.update($scope.customerDiscountRule, onSaveSuccess, onSaveError);
            } else {
                CustomerDiscountRule.save($scope.customerDiscountRule, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
