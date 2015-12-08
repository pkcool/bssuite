'use strict';

angular.module('bssuiteApp')
	.controller('QuoteDeleteController', function($scope, $uibModalInstance, entity, Quote) {

        $scope.quote = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Quote.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
