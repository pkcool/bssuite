'use strict';

angular.module('bssuiteApp')
    .controller('PurchaseOrderDetailController', function ($scope, $rootScope, $stateParams, entity, PurchaseOrder, Supplier, Contact, Store, SalesOrder, Staff, PurchaseOrderLineItem) {
        $scope.purchaseOrder = entity;
        $scope.load = function (id) {
            PurchaseOrder.get({id: id}, function(result) {
                $scope.purchaseOrder = result;
            });
        };
        $rootScope.$on('bssuiteApp:purchaseOrderUpdate', function(event, result) {
            $scope.purchaseOrder = result;
        });
    });
