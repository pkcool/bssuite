'use strict';

angular.module('bssuiteApp').controller('PurchaseOrderLineItemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PurchaseOrderLineItem', 'PurchaseOrder', 'Product', 'TaxTable',
        function($scope, $stateParams, $uibModalInstance, entity, PurchaseOrderLineItem, PurchaseOrder, Product, TaxTable) {

        $scope.purchaseOrderLineItem = entity;
        $scope.purchaseorders = PurchaseOrder.query();
        $scope.products = Product.query();
        $scope.taxtables = TaxTable.query();
        $scope.load = function(id) {
            PurchaseOrderLineItem.get({id : id}, function(result) {
                $scope.purchaseOrderLineItem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:purchaseOrderLineItemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.purchaseOrderLineItem.id != null) {
                PurchaseOrderLineItem.update($scope.purchaseOrderLineItem, onSaveSuccess, onSaveError);
            } else {
                PurchaseOrderLineItem.save($scope.purchaseOrderLineItem, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
