'use strict';

angular.module('bssuiteApp')
	.controller('PromotionDeleteController', function($scope, $uibModalInstance, entity, Promotion) {

        $scope.promotion = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Promotion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
