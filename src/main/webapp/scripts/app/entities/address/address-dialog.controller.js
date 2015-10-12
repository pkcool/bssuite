'use strict';

angular.module('bssuiteApp').controller('AddressDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Address',
        function($scope, $stateParams, $modalInstance, entity, Address) {

        $scope.address = entity;
        $scope.load = function(id) {
            Address.get({id : id}, function(result) {
                $scope.address = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:addressUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.address.id != null) {
                Address.update($scope.address, onSaveFinished);
            } else {
                Address.save($scope.address, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
