'use strict';

angular.module('bssuiteApp')
	.controller('AddressDeleteController', function($scope, $modalInstance, entity, Address) {

        $scope.address = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Address.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });