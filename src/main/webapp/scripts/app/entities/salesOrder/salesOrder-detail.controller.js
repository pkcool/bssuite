'use strict';

angular.module('bssuiteApp')
    .controller('SalesOrderDetailController', function ($scope, $rootScope, $stateParams, entity, SalesOrder, Customer, Contact, Store, Carrier, Staff, Promotion, SalesOrderLineItem) {
        $scope.salesOrder = entity;
        $scope.load = function (id) {
            SalesOrder.get({id: id}, function(result) {
                $scope.salesOrder = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:salesOrderUpdate', function(event, result) {
            $scope.salesOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
