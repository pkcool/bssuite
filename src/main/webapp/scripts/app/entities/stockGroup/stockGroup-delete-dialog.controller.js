'use strict';

angular.module('bssuiteApp')
	.controller('StockGroupDeleteController', function($scope, $uibModalInstance, entity, StockGroup) {

        $scope.stockGroup = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StockGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
