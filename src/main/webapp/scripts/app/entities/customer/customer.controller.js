'use strict';

angular.module('bssuiteApp')
    .controller('CustomerController', function ($scope, $state, Customer, CustomerSearch, ParseLinks) {

        $scope.customers = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Customer.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.customers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


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
