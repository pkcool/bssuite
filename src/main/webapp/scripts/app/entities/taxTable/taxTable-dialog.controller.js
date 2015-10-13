'use strict';

angular.module('bssuiteApp').controller('TaxTableDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TaxTable',
        function($scope, $stateParams, $modalInstance, entity, TaxTable) {

        $scope.taxTable = entity;
        $scope.load = function(id) {
            TaxTable.get({id : id}, function(result) {
                $scope.taxTable = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:taxTableUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.taxTable.id != null) {
                TaxTable.update($scope.taxTable, onSaveFinished);
            } else {
                TaxTable.save($scope.taxTable, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
