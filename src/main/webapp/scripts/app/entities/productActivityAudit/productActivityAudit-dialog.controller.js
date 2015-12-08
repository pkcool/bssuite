'use strict';

angular.module('bssuiteApp').controller('ProductActivityAuditDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductActivityAudit', 'Staff', 'Product',
        function($scope, $stateParams, $uibModalInstance, entity, ProductActivityAudit, Staff, Product) {

        $scope.productActivityAudit = entity;
        $scope.staffs = Staff.query();
        $scope.products = Product.query();
        $scope.load = function(id) {
            ProductActivityAudit.get({id : id}, function(result) {
                $scope.productActivityAudit = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:productActivityAuditUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.productActivityAudit.id != null) {
                ProductActivityAudit.update($scope.productActivityAudit, onSaveSuccess, onSaveError);
            } else {
                ProductActivityAudit.save($scope.productActivityAudit, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
