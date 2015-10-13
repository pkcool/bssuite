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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:stockFamilyUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.stockFamily.id != null) {
                StockFamily.update($scope.stockFamily, onSaveFinished);
            } else {
                StockFamily.save($scope.stockFamily, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
