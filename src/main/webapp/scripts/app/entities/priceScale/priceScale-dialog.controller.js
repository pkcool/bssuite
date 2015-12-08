'use strict';

angular.module('bssuiteApp').controller('PriceScaleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PriceScale',
        function($scope, $stateParams, $uibModalInstance, entity, PriceScale) {

        $scope.priceScale = entity;
        $scope.load = function(id) {
            PriceScale.get({id : id}, function(result) {
                $scope.priceScale = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:priceScaleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.priceScale.id != null) {
                PriceScale.update($scope.priceScale, onSaveSuccess, onSaveError);
            } else {
                PriceScale.save($scope.priceScale, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
