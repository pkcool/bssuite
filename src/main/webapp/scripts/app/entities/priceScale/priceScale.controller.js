'use strict';

angular.module('bssuiteApp')
    .controller('PriceScaleController', function ($scope, PriceScale, PriceScaleSearch, ParseLinks) {
        $scope.priceScales = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PriceScale.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.priceScales = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PriceScale.get({id: id}, function(result) {
                $scope.priceScale = result;
                $('#deletePriceScaleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PriceScale.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePriceScaleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
