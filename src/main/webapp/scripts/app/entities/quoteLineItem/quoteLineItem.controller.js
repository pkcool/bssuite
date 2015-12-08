'use strict';

angular.module('bssuiteApp')
    .controller('QuoteLineItemController', function ($scope, $state, QuoteLineItem, QuoteLineItemSearch, ParseLinks) {

        $scope.quoteLineItems = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            QuoteLineItem.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.quoteLineItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            QuoteLineItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.quoteLineItems = result;
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
            $scope.quoteLineItem = {
                description: null,
                cost: null,
                soldFor: null,
                qtyQuoted: null,
                discountPriceGroupCode: null,
                totalTaxCharge: null,
                discountPercentage: null,
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
