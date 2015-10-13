'use strict';

angular.module('bssuiteApp')
    .controller('PriceScaleDetailController', function ($scope, $rootScope, $stateParams, entity, PriceScale) {
        $scope.priceScale = entity;
        $scope.load = function (id) {
            PriceScale.get({id: id}, function(result) {
                $scope.priceScale = result;
            });
        };
        $rootScope.$on('bssuiteApp:priceScaleUpdate', function(event, result) {
            $scope.priceScale = result;
        });
    });
