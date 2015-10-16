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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:quoteLineItemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.quoteLineItem.id != null) {
                QuoteLineItem.update($scope.quoteLineItem, onSaveFinished);
            } else {
                QuoteLineItem.save($scope.quoteLineItem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
