'use strict';

angular.module('bssuiteApp')
    .controller('QuoteLineItemController', function ($scope, QuoteLineItem, QuoteLineItemSearch, ParseLinks) {
        $scope.quoteLineItems = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            QuoteLineItem.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.quoteLineItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            QuoteLineItem.get({id: id}, function(result) {
                $scope.quoteLineItem = result;
                $('#deleteQuoteLineItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            QuoteLineItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteQuoteLineItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
