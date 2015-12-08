'use strict';

angular.module('bssuiteApp')
    .controller('PurchaseOrderController', function ($scope, $state, PurchaseOrder, PurchaseOrderSearch, ParseLinks) {

        $scope.purchaseOrders = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PurchaseOrder.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.purchaseOrders = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


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
