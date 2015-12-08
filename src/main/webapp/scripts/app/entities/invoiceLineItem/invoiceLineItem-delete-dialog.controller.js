'use strict';

angular.module('bssuiteApp')
	.controller('InvoiceLineItemDeleteController', function($scope, $uibModalInstance, entity, InvoiceLineItem) {

        $scope.invoiceLineItem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InvoiceLineItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
