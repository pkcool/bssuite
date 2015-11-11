'use strict';

angular.module('bssuiteApp')
	.controller('CarrierDeleteController', function($scope, $modalInstance, entity, Carrier) {

        $scope.carrier = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Carrier.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });