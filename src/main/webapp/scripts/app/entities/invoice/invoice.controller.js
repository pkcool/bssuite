'use strict';

angular.module('bssuiteApp')
    .controller('InvoiceController', function ($scope, Invoice, InvoiceSearch, ParseLinks) {
        $scope.invoices = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Invoice.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.invoices = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Invoice.get({id: id}, function(result) {
                $scope.invoice = result;
                $('#deleteInvoiceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Invoice.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInvoiceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InvoiceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.invoices = result;
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
            $scope.invoice = {
                invoiceNo: null,
                invoiceTxnType: null,
                txnDate: null,
                dueDate: null,
                reference: null,
                ourRef: null,
                ourRef2: null,
                ourRef3: null,
                taxExemptionCode: null,
                isPenaltyIssued: null,
                freight: null,
                handlingCharge: null,
                charge2: null,
                isTaxable: null,
                isLocked: null,
                adjustTax: null,
                adjustTaxExempt: null,
                paymentCash: null,
                paymentCheque: null,
                paymentCreditCard: null,
                paymentDirectDeposit: null,
                paymentVoucher: null,
                paymentDirectDebit: null,
                payment1: null,
                payment2: null,
                bank: null,
                bankBranch: null,
                bankAccount: null,
                dateOfDeposit: null,
                drawerName: null,
                NoAlloc: null,
                coinRound: null,
                prepayment: null,
                in2: null,
                comment: null,
                totalTaxAmount: null,
                totalSellPrice: null,
                totalCost: null,
                isOnHold: null,
                isLayBy: null,
                isExternalTxn: null,
                isSuspended: null,
                generatedFrom: null,
                id: null
            };
        };
    });
