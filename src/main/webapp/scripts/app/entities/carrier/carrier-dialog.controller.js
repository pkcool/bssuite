'use strict';

angular.module('bssuiteApp').controller('CarrierDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Carrier',
        function($scope, $stateParams, $modalInstance, entity, Carrier) {

        $scope.carrier = entity;
        $scope.load = function(id) {
            Carrier.get({id : id}, function(result) {
                $scope.carrier = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:carrierUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.carrier.id != null) {
                Carrier.update($scope.carrier, onSaveSuccess, onSaveError);
            } else {
                Carrier.save($scope.carrier, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
