'use strict';

angular.module('bssuiteApp')
    .controller('PromotionController', function ($scope, $state, $modal, Promotion, PromotionSearch, ParseLinks) {
      
        $scope.promotions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Promotion.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.promotions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PromotionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.promotions = result;
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
            $scope.promotion = {
                code: null,
                name: null,
                description: null,
                startDate: null,
                endDate: null,
                cost: null,
                income: null,
                expense: null,
                dateCreated: null,
                id: null
            };
        };
    });
