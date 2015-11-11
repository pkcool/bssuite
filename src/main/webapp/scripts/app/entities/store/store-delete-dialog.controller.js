'use strict';

angular.module('bssuiteApp')
	.controller('StoreDeleteController', function($scope, $modalInstance, entity, Store) {

        $scope.store = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Store.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });