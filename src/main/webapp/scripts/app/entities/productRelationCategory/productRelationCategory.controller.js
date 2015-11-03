'use strict';

angular.module('bssuiteApp')
    .controller('ProductRelationCategoryController', function ($scope, ProductRelationCategory, ProductRelationCategorySearch, ParseLinks) {
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

        $scope.delete = function (id) {
            ProductRelationCategory.get({id: id}, function(result) {
                $scope.productRelationCategory = result;
                $('#deleteProductRelationCategoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ProductRelationCategory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProductRelationCategoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
