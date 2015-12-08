'use strict';

angular.module('bssuiteApp')
	.controller('GoodsReceivedAuditDeleteController', function($scope, $uibModalInstance, entity, GoodsReceivedAudit) {

        $scope.goodsReceivedAudit = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GoodsReceivedAudit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
