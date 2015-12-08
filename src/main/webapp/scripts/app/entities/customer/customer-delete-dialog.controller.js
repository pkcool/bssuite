'use strict';

angular.module('bssuiteApp')
	.controller('CustomerDeleteController', function($scope, $uibModalInstance, entity, Customer) {

        $scope.customer = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Customer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
