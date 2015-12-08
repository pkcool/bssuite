'use strict';

angular.module('bssuiteApp')
    .controller('SalesOrderLineItemController', function ($scope, $state, SalesOrderLineItem, SalesOrderLineItemSearch, ParseLinks) {

        $scope.salesOrderLineItems = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SalesOrderLineItem.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.salesOrderLineItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SalesOrderLineItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.salesOrderLineItems = result;
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
            $scope.salesOrderLineItem = {
                description: null,
                cost: null,
                soldFor: null,
                qtyOrdered: null,
                qtyAllocated: null,
                totalTaxCharge: null,
                discountPercentage: null,
                lineNo: null,
                requiredDate: null,
                listPrice: null,
                listPriceDiscount: null,
                cost2: null,
                isHidden: null,
                Ref1: null,
                Ref2: null,
                id: null
            };
        };
    });
