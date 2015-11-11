'use strict';

angular.module('bssuiteApp')
	.controller('PromotionDeleteController', function($scope, $modalInstance, entity, Promotion) {

        $scope.promotion = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Promotion.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });