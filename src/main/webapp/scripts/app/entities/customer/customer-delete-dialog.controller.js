'use strict';

angular.module('bssuiteApp')
	.controller('CustomerDeleteController', function($scope, $modalInstance, entity, Customer) {

        $scope.customer = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Customer.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });