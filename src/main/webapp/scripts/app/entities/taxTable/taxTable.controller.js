'use strict';

angular.module('bssuiteApp')
    .controller('TaxTableController', function ($scope, $state, $modal, TaxTable, TaxTableSearch, ParseLinks) {
      
        $scope.taxTables = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TaxTable.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.taxTables = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            TaxTableSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.taxTables = result;
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
            $scope.taxTable = {
                code: null,
                name: null,
                formula: null,
                isAddedToTotal: null,
                isSubtractedFromTotal: null,
                isExcludedOnReporting: null,
                taxGroup: null,
                taxBase: null,
                id: null
            };
        };
    });
