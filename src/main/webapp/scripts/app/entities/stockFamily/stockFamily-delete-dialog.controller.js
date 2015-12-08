'use strict';

angular.module('bssuiteApp')
	.controller('StockFamilyDeleteController', function($scope, $uibModalInstance, entity, StockFamily) {

        $scope.stockFamily = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StockFamily.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
