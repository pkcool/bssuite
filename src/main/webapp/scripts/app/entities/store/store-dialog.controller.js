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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:storeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.store.id != null) {
                Store.update($scope.store, onSaveFinished);
            } else {
                Store.save($scope.store, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
