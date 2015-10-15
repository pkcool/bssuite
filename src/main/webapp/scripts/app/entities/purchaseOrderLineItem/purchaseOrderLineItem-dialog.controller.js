'use strict';

angular.module('bssuiteApp').controller('PurchaseOrderLineItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PurchaseOrderLineItem', 'PurchaseOrder', 'Product', 'TaxTable',
        function($scope, $stateParams, $modalInstance, entity, PurchaseOrderLineItem, PurchaseOrder, Product, TaxTable) {

        $scope.purchaseOrderLineItem = entity;
        $scope.purchaseorders = PurchaseOrder.query();
        $scope.products = Product.query();
        $scope.taxtables = TaxTable.query();
        $scope.load = function(id) {
            PurchaseOrderLineItem.get({id : id}, function(result) {
                $scope.purchaseOrderLineItem = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:purchaseOrderLineItemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.purchaseOrderLineItem.id != null) {
                PurchaseOrderLineItem.update($scope.purchaseOrderLineItem, onSaveFinished);
            } else {
                PurchaseOrderLineItem.save($scope.purchaseOrderLineItem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
