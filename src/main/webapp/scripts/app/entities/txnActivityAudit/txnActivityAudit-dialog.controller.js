'use strict';

angular.module('bssuiteApp').controller('TxnActivityAuditDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TxnActivityAudit', 'Staff',
        function($scope, $stateParams, $modalInstance, entity, TxnActivityAudit, Staff) {

        $scope.txnActivityAudit = entity;
        $scope.staffs = Staff.query();
        $scope.load = function(id) {
            TxnActivityAudit.get({id : id}, function(result) {
                $scope.txnActivityAudit = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:txnActivityAuditUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.txnActivityAudit.id != null) {
                TxnActivityAudit.update($scope.txnActivityAudit, onSaveFinished);
            } else {
                TxnActivityAudit.save($scope.txnActivityAudit, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
