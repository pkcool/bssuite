'use strict';

angular.module('bssuiteApp')
	.controller('QuoteLineItemDeleteController', function($scope, $uibModalInstance, entity, QuoteLineItem) {

        $scope.quoteLineItem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            QuoteLineItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
