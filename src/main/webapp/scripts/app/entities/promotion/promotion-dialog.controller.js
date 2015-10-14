'use strict';

angular.module('bssuiteApp').controller('PromotionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Promotion', 'Store',
        function($scope, $stateParams, $modalInstance, entity, Promotion, Store) {

        $scope.promotion = entity;
        $scope.stores = Store.query();
        $scope.load = function(id) {
            Promotion.get({id : id}, function(result) {
                $scope.promotion = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:promotionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.promotion.id != null) {
                Promotion.update($scope.promotion, onSaveFinished);
            } else {
                Promotion.save($scope.promotion, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
