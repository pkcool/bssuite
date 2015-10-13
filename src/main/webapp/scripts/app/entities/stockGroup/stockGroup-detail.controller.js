'use strict';

angular.module('bssuiteApp')
    .controller('StockGroupDetailController', function ($scope, $rootScope, $stateParams, entity, StockGroup, StockFamily, PriceScale, TaxTable) {
        $scope.stockGroup = entity;
        $scope.load = function (id) {
            StockGroup.get({id: id}, function(result) {
                $scope.stockGroup = result;
            });
        };
        $rootScope.$on('bssuiteApp:stockGroupUpdate', function(event, result) {
            $scope.stockGroup = result;
        });
    });
