'use strict';

angular.module('bssuiteApp')
    .controller('CustomerDiscountRuleDetailController', function ($scope, $rootScope, $stateParams, entity, CustomerDiscountRule) {
        $scope.customerDiscountRule = entity;
        $scope.load = function (id) {
            CustomerDiscountRule.get({id: id}, function(result) {
                $scope.customerDiscountRule = result;
            });
        };
        $rootScope.$on('bssuiteApp:customerDiscountRuleUpdate', function(event, result) {
            $scope.customerDiscountRule = result;
        });
    });
