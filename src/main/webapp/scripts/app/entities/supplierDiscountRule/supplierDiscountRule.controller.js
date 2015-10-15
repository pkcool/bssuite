'use strict';

angular.module('bssuiteApp')
    .controller('SupplierDiscountRuleController', function ($scope, SupplierDiscountRule, SupplierDiscountRuleSearch, ParseLinks) {
        $scope.supplierDiscountRules = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            SupplierDiscountRule.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.supplierDiscountRules = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            SupplierDiscountRule.get({id: id}, function(result) {
                $scope.supplierDiscountRule = result;
                $('#deleteSupplierDiscountRuleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            SupplierDiscountRule.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSupplierDiscountRuleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
