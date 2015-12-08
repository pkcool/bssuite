'use strict';

angular.module('bssuiteApp')
	.controller('SalesOrderLineItemDeleteController', function($scope, $uibModalInstance, entity, SalesOrderLineItem) {

        $scope.salesOrderLineItem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SalesOrderLineItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
