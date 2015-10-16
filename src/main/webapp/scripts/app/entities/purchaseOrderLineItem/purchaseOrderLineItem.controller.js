'use strict';

angular.module('bssuiteApp')
    .controller('PurchaseOrderLineItemController', function ($scope, PurchaseOrderLineItem, PurchaseOrderLineItemSearch, ParseLinks) {
        $scope.purchaseOrderLineItems = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PurchaseOrderLineItem.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.purchaseOrderLineItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PurchaseOrderLineItem.get({id: id}, function(result) {
                $scope.purchaseOrderLineItem = result;
                $('#deletePurchaseOrderLineItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PurchaseOrderLineItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePurchaseOrderLineItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PurchaseOrderLineItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.purchaseOrderLineItems = result;
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
            $scope.purchaseOrderLineItem = {
                description: null,
                cost: null,
                listPrice: null,
                qtyOrdered: null,
                qtyDelivered: null,
                qtyPreviouslyDelivered: null,
                qtyImported: null,
                totalTaxCharge: null,
                discountPercentage: null,
                lineNo: null,
                listPriceDiscount: null,
                unitMeasure: null,
                isHidden: null,
                Ref1: null,
                Ref2: null,
                id: null
            };
        };
    });
