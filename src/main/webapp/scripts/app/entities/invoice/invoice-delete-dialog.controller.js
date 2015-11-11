'use strict';

angular.module('bssuiteApp')
	.controller('InvoiceDeleteController', function($scope, $modalInstance, entity, Invoice) {

        $scope.invoice = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Invoice.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });