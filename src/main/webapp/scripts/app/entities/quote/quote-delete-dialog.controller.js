'use strict';

angular.module('bssuiteApp')
	.controller('QuoteDeleteController', function($scope, $modalInstance, entity, Quote) {

        $scope.quote = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Quote.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });