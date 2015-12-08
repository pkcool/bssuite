'use strict';

angular.module('bssuiteApp').controller('GoodsReceivedAuditDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'GoodsReceivedAudit', 'Staff', 'Supplier', 'PurchaseOrder', 'Product',
        function($scope, $stateParams, $uibModalInstance, entity, GoodsReceivedAudit, Staff, Supplier, PurchaseOrder, Product) {

        $scope.goodsReceivedAudit = entity;
        $scope.staffs = Staff.query();
        $scope.suppliers = Supplier.query();
        $scope.purchaseorders = PurchaseOrder.query();
        $scope.products = Product.query();
        $scope.load = function(id) {
            GoodsReceivedAudit.get({id : id}, function(result) {
                $scope.goodsReceivedAudit = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:goodsReceivedAuditUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.goodsReceivedAudit.id != null) {
                GoodsReceivedAudit.update($scope.goodsReceivedAudit, onSaveSuccess, onSaveError);
            } else {
                GoodsReceivedAudit.save($scope.goodsReceivedAudit, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
