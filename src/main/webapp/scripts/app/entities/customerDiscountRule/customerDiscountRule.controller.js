'use strict';

angular.module('bssuiteApp')
    .controller('CustomerDiscountRuleController', function ($scope, $state, CustomerDiscountRule, CustomerDiscountRuleSearch, ParseLinks) {

        $scope.customerDiscountRules = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            CustomerDiscountRule.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.customerDiscountRules = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CustomerDiscountRuleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.customerDiscountRules = result;
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
            $scope.customerDiscountRule = {
                priceGroupCode: null,
                ruleNo: null,
                isAppliedGlobally: null,
                isAppliedOnSpecialItemsOnly: null,
                customerCode: null,
                customerCategoryCode: null,
                fromProductCode: null,
                toProductCode: null,
                startDate: null,
                endDate: null,
                qtyBreak: null,
                fromSupplierCode: null,
                toSupplierCode: null,
                fromStockGroupCode: null,
                toStockGroupCode: null,
                taxCode: null,
                isAppliedWhenTaxExempt: null,
                storeCode: null,
                discountName: null,
                stockFamilyCode: null,
                listPrice: null,
                discountFormula: null,
                id: null
            };
        };
    });
