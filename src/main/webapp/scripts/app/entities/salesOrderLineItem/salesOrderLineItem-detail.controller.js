'use strict';

angular.module('bssuiteApp')
    .controller('SalesOrderLineItemDetailController', function ($scope, $rootScope, $stateParams, entity, SalesOrderLineItem, SalesOrder, Product, TaxTable) {
        $scope.salesOrderLineItem = entity;
        $scope.load = function (id) {
            SalesOrderLineItem.get({id: id}, function(result) {
                $scope.salesOrderLineItem = result;
            });
        };
        $rootScope.$on('bssuiteApp:salesOrderLineItemUpdate', function(event, result) {
            $scope.salesOrderLineItem = result;
        });
    });
