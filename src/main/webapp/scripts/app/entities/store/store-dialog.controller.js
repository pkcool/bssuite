'use strict';

angular.module('bssuiteApp').controller('StoreDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Store',
        function($scope, $stateParams, $modalInstance, entity, Store) {

        $scope.store = entity;
        $scope.load = function(id) {
            Store.get({id : id}, function(result) {
                $scope.store = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:storeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.store.id != null) {
                Store.update($scope.store, onSaveSuccess, onSaveError);
            } else {
                Store.save($scope.store, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
