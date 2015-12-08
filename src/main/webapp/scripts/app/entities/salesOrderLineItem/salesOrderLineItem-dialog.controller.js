'use strict';

angular.module('bssuiteApp').controller('SalesOrderLineItemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SalesOrderLineItem', 'SalesOrder', 'Product', 'TaxTable',
        function($scope, $stateParams, $uibModalInstance, entity, SalesOrderLineItem, SalesOrder, Product, TaxTable) {

        $scope.salesOrderLineItem = entity;
        $scope.salesorders = SalesOrder.query();
        $scope.products = Product.query();
        $scope.taxtables = TaxTable.query();
        $scope.load = function(id) {
            SalesOrderLineItem.get({id : id}, function(result) {
                $scope.salesOrderLineItem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:salesOrderLineItemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.salesOrderLineItem.id != null) {
                SalesOrderLineItem.update($scope.salesOrderLineItem, onSaveSuccess, onSaveError);
            } else {
                SalesOrderLineItem.save($scope.salesOrderLineItem, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
