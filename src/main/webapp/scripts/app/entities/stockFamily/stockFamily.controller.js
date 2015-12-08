'use strict';

angular.module('bssuiteApp')
    .controller('StockFamilyController', function ($scope, $state, StockFamily, StockFamilySearch, ParseLinks) {

        $scope.stockFamilys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            StockFamily.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.stockFamilys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            StockFamilySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.stockFamilys = result;
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
            $scope.stockFamily = {
                code: null,
                name: null,
                isDiminishing: null,
                lowestMargin: null,
                highestMargin: null,
                comment: null,
                id: null
            };
        };
    });
