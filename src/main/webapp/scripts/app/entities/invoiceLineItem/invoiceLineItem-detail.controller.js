'use strict';

angular.module('bssuiteApp')
    .controller('InvoiceLineItemDetailController', function ($scope, $rootScope, $stateParams, entity, InvoiceLineItem, Invoice, Product, TaxTable) {
        $scope.invoiceLineItem = entity;
        $scope.load = function (id) {
            InvoiceLineItem.get({id: id}, function(result) {
                $scope.invoiceLineItem = result;
            });
        };
        $rootScope.$on('bssuiteApp:invoiceLineItemUpdate', function(event, result) {
            $scope.invoiceLineItem = result;
        });
    });
