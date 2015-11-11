'use strict';

angular.module('bssuiteApp').controller('PurchaseOrderDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PurchaseOrder', 'Supplier', 'Contact', 'Store', 'SalesOrder', 'Staff', 'PurchaseOrderLineItem',
        function($scope, $stateParams, $modalInstance, entity, PurchaseOrder, Supplier, Contact, Store, SalesOrder, Staff, PurchaseOrderLineItem) {

        $scope.purchaseOrder = entity;
        $scope.suppliers = Supplier.query();
        $scope.contacts = Contact.query();
        $scope.stores = Store.query();
        $scope.salesorders = SalesOrder.query();
        $scope.staffs = Staff.query();
        $scope.purchaseorderlineitems = PurchaseOrderLineItem.query();
        $scope.load = function(id) {
            PurchaseOrder.get({id : id}, function(result) {
                $scope.purchaseOrder = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:purchaseOrderUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.purchaseOrder.id != null) {
                PurchaseOrder.update($scope.purchaseOrder, onSaveSuccess, onSaveError);
            } else {
                PurchaseOrder.save($scope.purchaseOrder, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
