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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:invoiceUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.invoice.id != null) {
                Invoice.update($scope.invoice, onSaveFinished);
            } else {
                Invoice.save($scope.invoice, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
