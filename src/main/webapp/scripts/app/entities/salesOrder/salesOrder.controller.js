'use strict';

angular.module('bssuiteApp')
    .controller('SalesOrderController', function ($scope, $state, $modal, SalesOrder, SalesOrderSearch, ParseLinks) {
      
        $scope.salesOrders = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            SalesOrder.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.salesOrders = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SalesOrderSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.salesOrders = result;
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
            $scope.salesOrder = {
                orderNo: null,
                status: null,
                txnDate: null,
                forwardDate: null,
                requiredDate: null,
                customerOrderNo: null,
                ourRef: null,
                freight: null,
                handlingCharge: null,
                charge2: null,
                isTaxable: null,
                isLocked: null,
                adjustTax: null,
                adjustTaxExempt: null,
                prepayment: null,
                prepaymentNo: null,
                comment: null,
                totalTaxAmount: null,
                totalSellPrice: null,
                totalCost: null,
                isSuspended: null,
                id: null
            };
        };
    });
