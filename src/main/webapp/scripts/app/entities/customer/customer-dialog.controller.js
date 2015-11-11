'use strict';

angular.module('bssuiteApp').controller('CustomerDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Customer', 'CustomerCategory', 'Contact', 'Staff', 'Store',
        function($scope, $stateParams, $modalInstance, entity, Customer, CustomerCategory, Contact, Staff, Store) {

        $scope.customer = entity;
        $scope.customercategorys = CustomerCategory.query();
        $scope.contacts = Contact.query();
        $scope.staffs = Staff.query();
        $scope.customers = Customer.query();
        $scope.stores = Store.query();
        $scope.load = function(id) {
            Customer.get({id : id}, function(result) {
                $scope.customer = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:customerUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.customer.id != null) {
                Customer.update($scope.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save($scope.customer, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
