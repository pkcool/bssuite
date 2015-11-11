'use strict';

angular.module('bssuiteApp')
    .controller('GoodsReceivedAuditDetailController', function ($scope, $rootScope, $stateParams, entity, GoodsReceivedAudit, Staff, Supplier, PurchaseOrder, Product) {
        $scope.goodsReceivedAudit = entity;
        $scope.load = function (id) {
            GoodsReceivedAudit.get({id: id}, function(result) {
                $scope.goodsReceivedAudit = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:goodsReceivedAuditUpdate', function(event, result) {
            $scope.goodsReceivedAudit = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
