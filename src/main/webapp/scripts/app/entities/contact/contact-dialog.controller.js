'use strict';

angular.module('bssuiteApp').controller('ContactDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Contact', 'Address',
        function($scope, $stateParams, $modalInstance, $q, entity, Contact, Address) {

        $scope.contact = entity;
        $scope.addresss = Address.query({filter: 'contact-is-null'});
        $q.all([$scope.contact.$promise, $scope.addresss.$promise]).then(function() {
            if (!$scope.contact.addressId) {
                return $q.reject();
            }
            return Address.get({id : $scope.contact.addressId}).$promise;
        }).then(function(address) {
            $scope.addresss.push(address);
        });
        $scope.load = function(id) {
            Contact.get({id : id}, function(result) {
                $scope.contact = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:contactUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contact.id != null) {
                Contact.update($scope.contact, onSaveSuccess, onSaveError);
            } else {
                Contact.save($scope.contact, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
