'use strict';

angular.module('bssuiteApp')
	.controller('InvoiceDeleteController', function($scope, $uibModalInstance, entity, Invoice) {

        $scope.invoice = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Invoice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
