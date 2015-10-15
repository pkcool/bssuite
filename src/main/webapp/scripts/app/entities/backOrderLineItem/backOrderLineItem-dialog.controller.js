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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:backOrderLineItemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.backOrderLineItem.id != null) {
                BackOrderLineItem.update($scope.backOrderLineItem, onSaveFinished);
            } else {
                BackOrderLineItem.save($scope.backOrderLineItem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
