'use strict';

angular.module('bssuiteApp').controller('ProductDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Product', 'StockGroup', 'Supplier', 'Store', 'TaxTable',
        function($scope, $stateParams, $modalInstance, entity, Product, StockGroup, Supplier, Store, TaxTable) {

        $scope.product = entity;
        $scope.stockgroups = StockGroup.query();
        $scope.suppliers = Supplier.query();
        $scope.stores = Store.query();
        $scope.taxtables = TaxTable.query();
        $scope.load = function(id) {
            Product.get({id : id}, function(result) {
                $scope.product = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:productUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.product.id != null) {
                Product.update($scope.product, onSaveFinished);
            } else {
                Product.save($scope.product, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
