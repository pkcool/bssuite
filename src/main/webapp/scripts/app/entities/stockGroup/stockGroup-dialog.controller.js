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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:stockGroupUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.stockGroup.id != null) {
                StockGroup.update($scope.stockGroup, onSaveFinished);
            } else {
                StockGroup.save($scope.stockGroup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
