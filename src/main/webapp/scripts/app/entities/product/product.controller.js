'use strict';

angular.module('bssuiteApp')
    .controller('ProductController', function ($scope, $state, Product, ProductSearch, ParseLinks) {

        $scope.products = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Product.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.products = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProductSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.products = result;
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
            $scope.product = {
                code: null,
                name: null,
                description: null,
                longDescription: null,
                alternateCode: null,
                bin: null,
                isOnSpecial: null,
                isOnHold: null,
                isInPricelistReports: null,
                qtyOnOrder: null,
                qtyStockOnHold: null,
                qtyBackordered: null,
                qtyAllocated: null,
                qtyBackorderHold: null,
                qtyConsigned: null,
                qtyWarehouseReceived: null,
                qtyStocktakeVariance: null,
                qtyTransitIn: null,
                qtyTransitOut: null,
                cost: null,
                wholesaleListPrice: null,
                listPrice: null,
                tradePrice: null,
                boxCost: null,
                unitMeasure: null,
                boxMeasure: null,
                boxConversionFactor: null,
                weight: null,
                volumn: null,
                serviceCover: null,
                qtyFloorLevel: null,
                qtyReorderLevel: null,
                qtyOverstockLevel: null,
                isComment: null,
                isDiminishing: null,
                isNonTaxExeptable: null,
                leadTime: null,
                purchaseUnit: null,
                estMonthlySales: null,
                dateFirstSale: null,
                dateLastSale: null,
                dateFirstOrder: null,
                dateCreated: null,
                dateLastDelivery: null,
                dateNextDelivery: null,
                dateLastTransfer: null,
                dateLastOrder: null,
                dateLastStocktake: null,
                isArchived: null,
                classCode: null,
                id: null
            };
        };
    });
