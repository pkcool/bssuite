'use strict';

angular.module('bssuiteApp')
	.controller('InvoiceLineItemDeleteController', function($scope, $modalInstance, entity, InvoiceLineItem) {

        $scope.invoiceLineItem = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InvoiceLineItem.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });