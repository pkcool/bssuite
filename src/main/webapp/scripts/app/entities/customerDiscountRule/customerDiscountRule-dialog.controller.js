'use strict';

angular.module('bssuiteApp').controller('CustomerDiscountRuleDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CustomerDiscountRule',
        function($scope, $stateParams, $modalInstance, entity, CustomerDiscountRule) {

        $scope.customerDiscountRule = entity;
        $scope.load = function(id) {
            CustomerDiscountRule.get({id : id}, function(result) {
                $scope.customerDiscountRule = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:customerDiscountRuleUpdate', result);
            $modalInstance.close(result);
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
            $modalInstance.dismiss('cancel');
        };
}]);
