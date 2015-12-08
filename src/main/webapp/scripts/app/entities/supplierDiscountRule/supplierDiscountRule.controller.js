'use strict';

angular.module('bssuiteApp')
    .controller('SupplierDiscountRuleController', function ($scope, $state, SupplierDiscountRule, SupplierDiscountRuleSearch, ParseLinks) {

        $scope.supplierDiscountRules = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SupplierDiscountRule.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.supplierDiscountRules = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SupplierDiscountRuleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.supplierDiscountRules = result;
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
            $scope.supplierDiscountRule = {
                supplierCode: null,
                ruleNo: null,
                isAppliedToSales: null,
                isAppliedOnSpecialItemsOnly: null,
                customerCode: null,
                customerCategoryCode: null,
                productCode: null,
                startDate: null,
                endDate: null,
                qtyBreak: null,
                fromStockGroupCode: null,
                toStockGroupCode: null,
                taxCode: null,
                isAppliedWhenTaxExempt: null,
                storeCode: null,
                discountName: null,
                stockFamilyCode: null,
                cost: null,
                discountFormula: null,
                id: null
            };
        };
    });
