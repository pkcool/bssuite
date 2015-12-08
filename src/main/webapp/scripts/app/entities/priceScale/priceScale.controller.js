'use strict';

angular.module('bssuiteApp')
    .controller('PriceScaleController', function ($scope, $state, PriceScale, PriceScaleSearch, ParseLinks) {

        $scope.priceScales = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PriceScale.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.priceScales = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PriceScaleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.priceScales = result;
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
            $scope.priceScale = {
                code: null,
                name: null,
                price: null,
                value: null,
                round: null,
                id: null
            };
        };
    });
