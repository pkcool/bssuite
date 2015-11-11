'use strict';

angular.module('bssuiteApp').controller('SupplierDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Supplier', 'SupplierCategory', 'Contact',
        function($scope, $stateParams, $modalInstance, $q, entity, Supplier, SupplierCategory, Contact) {

        $scope.supplier = entity;
        $scope.suppliercategorys = SupplierCategory.query();
        $scope.contacts = Contact.query({filter: 'supplier-is-null'});
        $q.all([$scope.supplier.$promise, $scope.contacts.$promise]).then(function() {
            if (!$scope.supplier.contactId) {
                return $q.reject();
            }
            return Contact.get({id : $scope.supplier.contactId}).$promise;
        }).then(function(contact) {
            $scope.contacts.push(contact);
        });
        $scope.load = function(id) {
            Supplier.get({id : id}, function(result) {
                $scope.supplier = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:supplierUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.supplier.id != null) {
                Supplier.update($scope.supplier, onSaveSuccess, onSaveError);
            } else {
                Supplier.save($scope.supplier, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
