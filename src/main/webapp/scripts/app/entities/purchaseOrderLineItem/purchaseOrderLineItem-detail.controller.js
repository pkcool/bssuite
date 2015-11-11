'use strict';

angular.module('bssuiteApp')
    .controller('PurchaseOrderLineItemDetailController', function ($scope, $rootScope, $stateParams, entity, PurchaseOrderLineItem, PurchaseOrder, Product, TaxTable) {
        $scope.purchaseOrderLineItem = entity;
        $scope.load = function (id) {
            PurchaseOrderLineItem.get({id: id}, function(result) {
                $scope.purchaseOrderLineItem = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:purchaseOrderLineItemUpdate', function(event, result) {
            $scope.purchaseOrderLineItem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
