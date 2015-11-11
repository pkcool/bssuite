'use strict';

angular.module('bssuiteApp')
    .controller('StockGroupController', function ($scope, $state, $modal, StockGroup, StockGroupSearch, ParseLinks) {
      
        $scope.stockGroups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            StockGroup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.stockGroups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            StockGroupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.stockGroups = result;
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
            $scope.stockGroup = {
                code: null,
                name: null,
                isDiminishing: null,
                lowestMargin: null,
                highestMargin: null,
                isDiscountAllowed: null,
                comment: null,
                isArchived: null,
                id: null
            };
        };
    });
