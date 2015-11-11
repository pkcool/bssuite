'use strict';

angular.module('bssuiteApp')
	.controller('SalesOrderLineItemDeleteController', function($scope, $modalInstance, entity, SalesOrderLineItem) {

        $scope.salesOrderLineItem = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SalesOrderLineItem.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });