'use strict';

angular.module('bssuiteApp').controller('BackOrderLineItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'BackOrderLineItem', 'SalesOrderLineItem',
        function($scope, $stateParams, $modalInstance, entity, BackOrderLineItem, SalesOrderLineItem) {

        $scope.backOrderLineItem = entity;
        $scope.salesorderlineitems = SalesOrderLineItem.query();
        $scope.load = function(id) {
            BackOrderLineItem.get({id : id}, function(result) {
                $scope.backOrderLineItem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:backOrderLineItemUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.backOrderLineItem.id != null) {
                BackOrderLineItem.update($scope.backOrderLineItem, onSaveSuccess, onSaveError);
            } else {
                BackOrderLineItem.save($scope.backOrderLineItem, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
