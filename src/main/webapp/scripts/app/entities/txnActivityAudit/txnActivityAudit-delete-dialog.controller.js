'use strict';

angular.module('bssuiteApp')
	.controller('TxnActivityAuditDeleteController', function($scope, $modalInstance, entity, TxnActivityAudit) {

        $scope.txnActivityAudit = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TxnActivityAudit.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });