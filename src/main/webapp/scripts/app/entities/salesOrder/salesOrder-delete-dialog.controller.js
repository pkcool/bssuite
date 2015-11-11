'use strict';

angular.module('bssuiteApp')
	.controller('SalesOrderDeleteController', function($scope, $modalInstance, entity, SalesOrder) {

        $scope.salesOrder = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SalesOrder.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });