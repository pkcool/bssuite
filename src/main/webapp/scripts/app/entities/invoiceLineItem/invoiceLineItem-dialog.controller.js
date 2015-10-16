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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:invoiceLineItemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.invoiceLineItem.id != null) {
                InvoiceLineItem.update($scope.invoiceLineItem, onSaveFinished);
            } else {
                InvoiceLineItem.save($scope.invoiceLineItem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
