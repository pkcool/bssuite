'use strict';

angular.module('bssuiteApp')
	.controller('GoodsReceivedAuditDeleteController', function($scope, $modalInstance, entity, GoodsReceivedAudit) {

        $scope.goodsReceivedAudit = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GoodsReceivedAudit.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });