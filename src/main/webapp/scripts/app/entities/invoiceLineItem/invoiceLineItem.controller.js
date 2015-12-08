'use strict';

angular.module('bssuiteApp')
    .controller('InvoiceLineItemController', function ($scope, $state, InvoiceLineItem, InvoiceLineItemSearch, ParseLinks) {

        $scope.invoiceLineItems = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            InvoiceLineItem.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.invoiceLineItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InvoiceLineItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.invoiceLineItems = result;
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
            $scope.invoiceLineItem = {
                description: null,
                cost: null,
                soldFor: null,
                qtyOrdered: null,
                qtySold: null,
                qtyReturned: null,
                qtyPicked: null,
                totalTaxCharge: null,
                discountPercentage: null,
                discountDescription: null,
                discountPriceGroupCode: null,
                lineNo: null,
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
