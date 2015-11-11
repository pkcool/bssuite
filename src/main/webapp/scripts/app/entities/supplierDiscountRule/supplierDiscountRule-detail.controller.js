'use strict';

angular.module('bssuiteApp')
    .controller('SupplierDiscountRuleDetailController', function ($scope, $rootScope, $stateParams, entity, SupplierDiscountRule) {
        $scope.supplierDiscountRule = entity;
        $scope.load = function (id) {
            SupplierDiscountRule.get({id: id}, function(result) {
                $scope.supplierDiscountRule = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:supplierDiscountRuleUpdate', function(event, result) {
            $scope.supplierDiscountRule = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
