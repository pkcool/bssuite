'use strict';

angular.module('bssuiteApp').controller('StockFamilyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'StockFamily', 'PriceScale',
        function($scope, $stateParams, $modalInstance, entity, StockFamily, PriceScale) {

        $scope.stockFamily = entity;
        $scope.pricescales = PriceScale.query();
        $scope.load = function(id) {
            StockFamily.get({id : id}, function(result) {
                $scope.stockFamily = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:stockFamilyUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.stockFamily.id != null) {
                StockFamily.update($scope.stockFamily, onSaveSuccess, onSaveError);
            } else {
                StockFamily.save($scope.stockFamily, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
