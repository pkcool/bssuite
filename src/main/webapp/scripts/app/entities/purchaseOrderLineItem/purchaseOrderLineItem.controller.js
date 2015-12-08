'use strict';

angular.module('bssuiteApp')
    .controller('PurchaseOrderLineItemController', function ($scope, $state, PurchaseOrderLineItem, PurchaseOrderLineItemSearch, ParseLinks) {

        $scope.purchaseOrderLineItems = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PurchaseOrderLineItem.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.purchaseOrderLineItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PurchaseOrderLineItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.purchaseOrderLineItems = result;
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
            $scope.purchaseOrderLineItem = {
                description: null,
                cost: null,
                listPrice: null,
                qtyOrdered: null,
                qtyDelivered: null,
                qtyPreviouslyDelivered: null,
                qtyImported: null,
                totalTaxCharge: null,
                discountPercentage: null,
                lineNo: null,
                listPriceDiscount: null,
                unitMeasure: null,
                isHidden: null,
                Ref1: null,
                Ref2: null,
                id: null
            };
        };
    });
