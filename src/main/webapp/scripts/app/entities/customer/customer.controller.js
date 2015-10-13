'use strict';

angular.module('bssuiteApp')
    .controller('CustomerController', function ($scope, Customer, CustomerSearch, ParseLinks) {
        $scope.customers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Customer.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.customers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
                $('#deleteCustomerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Customer.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCustomerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            CustomerSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.customers = result;
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
            $scope.customer = {
                code: null,
                name: null,
                tradingName: null,
                abn: null,
                deliveryInstruction: null,
                webUrl: null,
                isOnHold: null,
                isOrderNoRequired: null,
                isBlacklisted: null,
                isBackorderAllowed: null,
                isArchived: null,
                isHeadOfficeAccount: null,
                ageingMethod: null,
                isWebaccessAllowed: null,
                bankName: null,
                bsb: null,
                bankAccountNo: null,
                bankSuburb: null,
                bankAccountName: null,
                isCreditCardBillingActive: null,
                creditCardNo: null,
                creditCardHolderName: null,
                creditCardExpiryDate: null,
                creditCardType: null,
                accountType: null,
                creditAmount: null,
                terms: null,
                settlementTerms: null,
                invoiceDeliveryMethod: null,
                statementDeliveryMethod: null,
                invoiceEmailAddress: null,
                invoiceFaxNo: null,
                statementEmailAddress: null,
                statementFaxNo: null,
                isPenaltyIssued: null,
                id: null
            };
        };
    });
