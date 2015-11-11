'use strict';

angular.module('bssuiteApp')
    .controller('ProductDetailController', function ($scope, $rootScope, $stateParams, entity, Product, StockGroup, Supplier, Store, TaxTable) {
        $scope.product = entity;
        $scope.load = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:productUpdate', function(event, result) {
            $scope.product = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
