'use strict';

angular.module('bssuiteApp')
    .controller('ProductRelationCategoryController', function ($scope, $state, $modal, ProductRelationCategory, ProductRelationCategorySearch, ParseLinks) {
      
        $scope.productRelationCategorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            ProductRelationCategory.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.productRelationCategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProductRelationCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.productRelationCategorys = result;
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
            $scope.productRelationCategory = {
                code: null,
                description: null,
                id: null
            };
        };
    });
