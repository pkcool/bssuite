'use strict';

angular.module('bssuiteApp').controller('StockGroupDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'StockGroup', 'StockFamily', 'PriceScale', 'TaxTable',
        function($scope, $stateParams, $modalInstance, entity, StockGroup, StockFamily, PriceScale, TaxTable) {

        $scope.stockGroup = entity;
        $scope.stockfamilys = StockFamily.query();
        $scope.pricescales = PriceScale.query();
        $scope.taxtables = TaxTable.query();
        $scope.load = function(id) {
            StockGroup.get({id : id}, function(result) {
                $scope.stockGroup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:stockGroupUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.stockGroup.id != null) {
                StockGroup.update($scope.stockGroup, onSaveSuccess, onSaveError);
            } else {
                StockGroup.save($scope.stockGroup, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
