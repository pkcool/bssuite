'use strict';

angular.module('bssuiteApp')
    .controller('InvoiceLineItemController', function ($scope, InvoiceLineItem, InvoiceLineItemSearch, ParseLinks) {
        $scope.invoiceLineItems = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InvoiceLineItem.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.invoiceLineItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InvoiceLineItem.get({id: id}, function(result) {
                $scope.invoiceLineItem = result;
                $('#deleteInvoiceLineItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InvoiceLineItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInvoiceLineItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InvoiceLineItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.invoiceLineItems = result;
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
            $scope.invoiceLineItem = {
                description: null,
                cost: null,
                soldFor: null,
                qtyOrdered: null,
                qtySold: null,
                qtyReturned: null,
                qtyPicked: null,
                taxCharge: null,
                discountPercentage: null,
                discountDescription: null,
                discountPriceGroupCode: null,
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
