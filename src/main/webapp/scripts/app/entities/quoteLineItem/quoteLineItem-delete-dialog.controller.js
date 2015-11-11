'use strict';

angular.module('bssuiteApp')
	.controller('QuoteLineItemDeleteController', function($scope, $modalInstance, entity, QuoteLineItem) {

        $scope.quoteLineItem = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            QuoteLineItem.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });