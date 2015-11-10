'use strict';

angular.module('bssuiteApp').controller('ProductActivityAuditDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ProductActivityAudit', 'Staff', 'Product',
        function($scope, $stateParams, $modalInstance, entity, ProductActivityAudit, Staff, Product) {

        $scope.productActivityAudit = entity;
        $scope.staffs = Staff.query();
        $scope.products = Product.query();
        $scope.load = function(id) {
            ProductActivityAudit.get({id : id}, function(result) {
                $scope.productActivityAudit = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:productActivityAuditUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.productActivityAudit.id != null) {
                ProductActivityAudit.update($scope.productActivityAudit, onSaveFinished);
            } else {
                ProductActivityAudit.save($scope.productActivityAudit, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
