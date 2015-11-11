'use strict';

angular.module('bssuiteApp')
	.controller('StockFamilyDeleteController', function($scope, $modalInstance, entity, StockFamily) {

        $scope.stockFamily = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StockFamily.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });