'use strict';

angular.module('bssuiteApp')
    .controller('QuoteController', function ($scope, $state, Quote, QuoteSearch, ParseLinks) {

        $scope.quotes = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Quote.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.quotes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            QuoteSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.quotes = result;
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
            $scope.quote = {
                quoteNo: null,
                status: null,
                quoteDate: null,
                expiryDate: null,
                followupDate: null,
                reference: null,
                ourRef: null,
                freight: null,
                reasonForLoss: null,
                isTaxable: null,
                taxExemptionCode: null,
                isLocked: null,
                adjustTax: null,
                adjustTaxExempt: null,
                comment: null,
                totalTaxAmount: null,
                totalSellPrice: null,
                totalCost: null,
                isSuspended: null,
                id: null
            };
        };
    });
