'use strict';

angular.module('bssuiteApp')
	.controller('TxnActivityAuditDeleteController', function($scope, $uibModalInstance, entity, TxnActivityAudit) {

        $scope.txnActivityAudit = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TxnActivityAudit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
