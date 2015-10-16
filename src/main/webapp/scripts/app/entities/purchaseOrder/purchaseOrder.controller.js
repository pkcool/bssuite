'use strict';

angular.module('bssuiteApp')
    .controller('PurchaseOrderController', function ($scope, PurchaseOrder, PurchaseOrderSearch, ParseLinks) {
        $scope.purchaseOrders = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PurchaseOrder.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.purchaseOrders = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PurchaseOrder.get({id: id}, function(result) {
                $scope.purchaseOrder = result;
                $('#deletePurchaseOrderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PurchaseOrder.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePurchaseOrderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PurchaseOrderSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.purchaseOrders = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.purchaseOrder = {
                orderNo: null,
                status: null,
                createdDate: null,
                ref: null,
                expectedDeliveryDate: null,
                isTaxable: null,
                isLocked: null,
                comment: null,
                totalTaxAmount: null,
                totalCost: null,
                taxExemptionCode: null,
                isSuspended: null,
                id: null
            };
        };
    });
