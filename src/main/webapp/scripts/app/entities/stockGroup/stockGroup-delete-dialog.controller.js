'use strict';

angular.module('bssuiteApp')
	.controller('StockGroupDeleteController', function($scope, $modalInstance, entity, StockGroup) {

        $scope.stockGroup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StockGroup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });