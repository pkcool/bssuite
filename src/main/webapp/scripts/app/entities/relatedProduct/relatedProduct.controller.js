'use strict';

angular.module('bssuiteApp')
    .controller('RelatedProductController', function ($scope, $state, RelatedProduct, RelatedProductSearch, ParseLinks) {

        $scope.relatedProducts = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RelatedProduct.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.relatedProducts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RelatedProductSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.relatedProducts = result;
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
            $scope.relatedProduct = {
                isSuggested: null,
                comment: null,
                id: null
            };
        };
    });
