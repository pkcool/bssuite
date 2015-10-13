'use strict';

angular.module('bssuiteApp').controller('PriceScaleDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PriceScale',
        function($scope, $stateParams, $modalInstance, entity, PriceScale) {

        $scope.priceScale = entity;
        $scope.load = function(id) {
            PriceScale.get({id : id}, function(result) {
                $scope.priceScale = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:priceScaleUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.priceScale.id != null) {
                PriceScale.update($scope.priceScale, onSaveFinished);
            } else {
                PriceScale.save($scope.priceScale, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
