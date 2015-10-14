'use strict';

angular.module('bssuiteApp')
    .controller('CustomerDiscountRuleController', function ($scope, CustomerDiscountRule, CustomerDiscountRuleSearch, ParseLinks) {
        $scope.customerDiscountRules = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CustomerDiscountRule.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.customerDiscountRules = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            CustomerDiscountRule.get({id: id}, function(result) {
                $scope.customerDiscountRule = result;
                $('#deleteCustomerDiscountRuleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CustomerDiscountRule.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCustomerDiscountRuleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
