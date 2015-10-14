'use strict';

angular.module('bssuiteApp').controller('SalesOrderLineItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SalesOrderLineItem', 'SalesOrder', 'Product', 'TaxTable',
        function($scope, $stateParams, $modalInstance, entity, SalesOrderLineItem, SalesOrder, Product, TaxTable) {

        $scope.salesOrderLineItem = entity;
        $scope.salesorders = SalesOrder.query();
        $scope.products = Product.query();
        $scope.taxtables = TaxTable.query();
        $scope.load = function(id) {
            SalesOrderLineItem.get({id : id}, function(result) {
                $scope.salesOrderLineItem = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:salesOrderLineItemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.salesOrderLineItem.id != null) {
                SalesOrderLineItem.update($scope.salesOrderLineItem, onSaveFinished);
            } else {
                SalesOrderLineItem.save($scope.salesOrderLineItem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
