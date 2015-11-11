'use strict';

angular.module('bssuiteApp').controller('InvoiceDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Invoice', 'Customer', 'Contact', 'Store', 'Carrier', 'Staff', 'Promotion', 'InvoiceLineItem',
        function($scope, $stateParams, $modalInstance, entity, Invoice, Customer, Contact, Store, Carrier, Staff, Promotion, InvoiceLineItem) {

        $scope.invoice = entity;
        $scope.customers = Customer.query();
        $scope.contacts = Contact.query();
        $scope.stores = Store.query();
        $scope.carriers = Carrier.query();
        $scope.staffs = Staff.query();
        $scope.promotions = Promotion.query();
        $scope.invoicelineitems = InvoiceLineItem.query();
        $scope.load = function(id) {
            Invoice.get({id : id}, function(result) {
                $scope.invoice = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:invoiceUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.invoice.id != null) {
                Invoice.update($scope.invoice, onSaveSuccess, onSaveError);
            } else {
                Invoice.save($scope.invoice, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
