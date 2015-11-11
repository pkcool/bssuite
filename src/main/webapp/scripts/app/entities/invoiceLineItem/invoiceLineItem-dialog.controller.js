'use strict';

angular.module('bssuiteApp').controller('InvoiceLineItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InvoiceLineItem', 'Invoice', 'Product', 'TaxTable',
        function($scope, $stateParams, $modalInstance, entity, InvoiceLineItem, Invoice, Product, TaxTable) {

        $scope.invoiceLineItem = entity;
        $scope.invoices = Invoice.query();
        $scope.products = Product.query();
        $scope.taxtables = TaxTable.query();
        $scope.load = function(id) {
            InvoiceLineItem.get({id : id}, function(result) {
                $scope.invoiceLineItem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:invoiceLineItemUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.invoiceLineItem.id != null) {
                InvoiceLineItem.update($scope.invoiceLineItem, onSaveSuccess, onSaveError);
            } else {
                InvoiceLineItem.save($scope.invoiceLineItem, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
