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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:carrierUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.carrier.id != null) {
                Carrier.update($scope.carrier, onSaveFinished);
            } else {
                Carrier.save($scope.carrier, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
