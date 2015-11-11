'use strict';

angular.module('bssuiteApp')
	.controller('PriceScaleDeleteController', function($scope, $modalInstance, entity, PriceScale) {

        $scope.priceScale = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PriceScale.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });