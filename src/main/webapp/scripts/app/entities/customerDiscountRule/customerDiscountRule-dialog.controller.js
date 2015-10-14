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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:customerDiscountRuleUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.customerDiscountRule.id != null) {
                CustomerDiscountRule.update($scope.customerDiscountRule, onSaveFinished);
            } else {
                CustomerDiscountRule.save($scope.customerDiscountRule, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
