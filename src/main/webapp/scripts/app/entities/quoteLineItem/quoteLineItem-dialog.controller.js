'use strict';

angular.module('bssuiteApp').controller('QuoteLineItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'QuoteLineItem', 'Quote', 'Product', 'TaxTable',
        function($scope, $stateParams, $modalInstance, entity, QuoteLineItem, Quote, Product, TaxTable) {

        $scope.quoteLineItem = entity;
        $scope.quotes = Quote.query();
        $scope.products = Product.query();
        $scope.taxtables = TaxTable.query();
        $scope.load = function(id) {
            QuoteLineItem.get({id : id}, function(result) {
                $scope.quoteLineItem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:quoteLineItemUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.quoteLineItem.id != null) {
                QuoteLineItem.update($scope.quoteLineItem, onSaveSuccess, onSaveError);
            } else {
                QuoteLineItem.save($scope.quoteLineItem, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
