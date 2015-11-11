'use strict';

angular.module('bssuiteApp').controller('SalesOrderDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SalesOrder', 'Customer', 'Contact', 'Store', 'Carrier', 'Staff', 'Promotion', 'SalesOrderLineItem',
        function($scope, $stateParams, $modalInstance, entity, SalesOrder, Customer, Contact, Store, Carrier, Staff, Promotion, SalesOrderLineItem) {

        $scope.salesOrder = entity;
        $scope.customers = Customer.query();
        $scope.contacts = Contact.query();
        $scope.stores = Store.query();
        $scope.carriers = Carrier.query();
        $scope.staffs = Staff.query();
        $scope.promotions = Promotion.query();
        $scope.salesorderlineitems = SalesOrderLineItem.query();
        $scope.load = function(id) {
            SalesOrder.get({id : id}, function(result) {
                $scope.salesOrder = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:salesOrderUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.salesOrder.id != null) {
                SalesOrder.update($scope.salesOrder, onSaveSuccess, onSaveError);
            } else {
                SalesOrder.save($scope.salesOrder, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
