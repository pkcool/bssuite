'use strict';

angular.module('bssuiteApp').controller('TaxTableDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TaxTable',
        function($scope, $stateParams, $uibModalInstance, entity, TaxTable) {

        $scope.taxTable = entity;
        $scope.load = function(id) {
            TaxTable.get({id : id}, function(result) {
                $scope.taxTable = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:taxTableUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.taxTable.id != null) {
                TaxTable.update($scope.taxTable, onSaveSuccess, onSaveError);
            } else {
                TaxTable.save($scope.taxTable, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
