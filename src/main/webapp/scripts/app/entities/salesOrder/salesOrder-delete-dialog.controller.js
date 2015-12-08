'use strict';

angular.module('bssuiteApp')
	.controller('SalesOrderDeleteController', function($scope, $uibModalInstance, entity, SalesOrder) {

        $scope.salesOrder = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SalesOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
