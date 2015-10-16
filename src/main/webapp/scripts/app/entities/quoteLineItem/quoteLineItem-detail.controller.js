'use strict';

angular.module('bssuiteApp')
    .controller('QuoteLineItemDetailController', function ($scope, $rootScope, $stateParams, entity, QuoteLineItem, Quote, Product, TaxTable) {
        $scope.quoteLineItem = entity;
        $scope.load = function (id) {
            QuoteLineItem.get({id: id}, function(result) {
                $scope.quoteLineItem = result;
            });
        };
        $rootScope.$on('bssuiteApp:quoteLineItemUpdate', function(event, result) {
            $scope.quoteLineItem = result;
        });
    });
