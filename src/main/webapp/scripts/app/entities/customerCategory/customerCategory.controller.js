'use strict';

angular.module('bssuiteApp')
    .controller('CustomerCategoryController', function ($scope, $state, $modal, CustomerCategory, CustomerCategorySearch, ParseLinks) {
      
        $scope.customerCategorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CustomerCategory.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.customerCategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CustomerCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.customerCategorys = result;
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
            $scope.customerCategory = {
                code: null,
                name: null,
                id: null
            };
        };
    });
