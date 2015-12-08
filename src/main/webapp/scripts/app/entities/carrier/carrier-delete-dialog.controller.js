'use strict';

angular.module('bssuiteApp')
	.controller('CarrierDeleteController', function($scope, $uibModalInstance, entity, Carrier) {

        $scope.carrier = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Carrier.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
