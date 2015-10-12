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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:supplierUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.supplier.id != null) {
                Supplier.update($scope.supplier, onSaveFinished);
            } else {
                Supplier.save($scope.supplier, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
