'use strict';

angular.module('bssuiteApp')
    .controller('StockGroupController', function ($scope, $state, StockGroup, StockGroupSearch, ParseLinks) {

        $scope.stockGroups = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            StockGroup.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
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
