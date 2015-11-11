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

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:txnActivityAuditUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.txnActivityAudit.id != null) {
                TxnActivityAudit.update($scope.txnActivityAudit, onSaveSuccess, onSaveError);
            } else {
                TxnActivityAudit.save($scope.txnActivityAudit, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
