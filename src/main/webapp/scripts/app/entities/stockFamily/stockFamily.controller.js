'use strict';

angular.module('bssuiteApp')
    .controller('StockFamilyController', function ($scope, $state, $modal, StockFamily, StockFamilySearch, ParseLinks) {
      
        $scope.stockFamilys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            StockFamily.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
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
