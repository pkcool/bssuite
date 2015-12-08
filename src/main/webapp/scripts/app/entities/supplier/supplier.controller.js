'use strict';

angular.module('bssuiteApp')
    .controller('SupplierController', function ($scope, $state, Supplier, SupplierSearch, ParseLinks) {

        $scope.suppliers = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Supplier.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.suppliers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SupplierSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.suppliers = result;
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
            $scope.supplier = {
                code: null,
                name: null,
                webUrl: null,
                comment: null,
                isOnHold: null,
                isHeadOffice: null,
                leadTime: null,
                accountType: null,
                settlementTerms: null,
                credit: null,
                terms: null,
                ageingMethod: null,
                isEFTPaymentsUsed: null,
                bankBSB: null,
                bankNumber: null,
                bankAccount: null,
                id: null
            };
        };
    });
