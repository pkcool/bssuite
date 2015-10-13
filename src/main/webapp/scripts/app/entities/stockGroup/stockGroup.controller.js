'use strict';

angular.module('bssuiteApp')
    .controller('StockGroupController', function ($scope, StockGroup, StockGroupSearch, ParseLinks) {
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

        $scope.delete = function (id) {
            StockGroup.get({id: id}, function(result) {
                $scope.stockGroup = result;
                $('#deleteStockGroupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            StockGroup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStockGroupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
