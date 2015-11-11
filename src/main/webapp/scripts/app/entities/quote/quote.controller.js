'use strict';

angular.module('bssuiteApp')
    .controller('QuoteController', function ($scope, $state, $modal, Quote, QuoteSearch, ParseLinks) {
      
        $scope.quotes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Quote.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
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
