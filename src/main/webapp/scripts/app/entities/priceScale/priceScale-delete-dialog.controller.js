'use strict';

angular.module('bssuiteApp')
	.controller('PriceScaleDeleteController', function($scope, $uibModalInstance, entity, PriceScale) {

        $scope.priceScale = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PriceScale.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
