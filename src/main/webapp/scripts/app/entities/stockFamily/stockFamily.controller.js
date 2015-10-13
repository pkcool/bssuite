'use strict';

angular.module('bssuiteApp')
    .controller('StockFamilyController', function ($scope, StockFamily, StockFamilySearch, ParseLinks) {
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

        $scope.delete = function (id) {
            StockFamily.get({id: id}, function(result) {
                $scope.stockFamily = result;
                $('#deleteStockFamilyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            StockFamily.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStockFamilyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
