'use strict';

angular.module('bssuiteApp')
    .controller('SalesOrderController', function ($scope, $state, SalesOrder, SalesOrderSearch, ParseLinks) {

        $scope.salesOrders = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SalesOrder.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
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
