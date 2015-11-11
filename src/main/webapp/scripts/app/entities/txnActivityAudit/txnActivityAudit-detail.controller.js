'use strict';

angular.module('bssuiteApp')
    .controller('TxnActivityAuditDetailController', function ($scope, $rootScope, $stateParams, entity, TxnActivityAudit, Staff) {
        $scope.txnActivityAudit = entity;
        $scope.load = function (id) {
            TxnActivityAudit.get({id: id}, function(result) {
                $scope.txnActivityAudit = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:txnActivityAuditUpdate', function(event, result) {
            $scope.txnActivityAudit = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
