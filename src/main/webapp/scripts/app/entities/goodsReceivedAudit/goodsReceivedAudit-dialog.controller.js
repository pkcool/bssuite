'use strict';

angular.module('bssuiteApp').controller('GoodsReceivedAuditDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GoodsReceivedAudit', 'Staff', 'Supplier', 'PurchaseOrder', 'Product',
        function($scope, $stateParams, $modalInstance, entity, GoodsReceivedAudit, Staff, Supplier, PurchaseOrder, Product) {

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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:goodsReceivedAuditUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.goodsReceivedAudit.id != null) {
                GoodsReceivedAudit.update($scope.goodsReceivedAudit, onSaveFinished);
            } else {
                GoodsReceivedAudit.save($scope.goodsReceivedAudit, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
