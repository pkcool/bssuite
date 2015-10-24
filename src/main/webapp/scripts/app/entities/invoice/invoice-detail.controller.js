'use strict';

angular.module('bssuiteApp')
    .controller('InvoiceDetailController', function ($scope, $rootScope, $stateParams, entity, Invoice, Customer, Contact, Store, Carrier, Staff, Promotion, InvoiceLineItem) {
        $scope.invoice = entity;
        $scope.load = function (id) {
            Invoice.get({id: id}, function(result) {
                $scope.invoice = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:invoiceUpdate', function(event, result) {
            $scope.invoice = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
