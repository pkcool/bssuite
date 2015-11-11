'use strict';

angular.module('bssuiteApp')
    .controller('PriceScaleDetailController', function ($scope, $rootScope, $stateParams, entity, PriceScale) {
        $scope.priceScale = entity;
        $scope.load = function (id) {
            PriceScale.get({id: id}, function(result) {
                $scope.priceScale = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:priceScaleUpdate', function(event, result) {
            $scope.priceScale = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
