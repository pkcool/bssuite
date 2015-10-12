'use strict';

angular.module('bssuiteApp')
    .controller('SupplierCategoryController', function ($scope, SupplierCategory, SupplierCategorySearch, ParseLinks) {
        $scope.supplierCategorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            SupplierCategory.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.supplierCategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            SupplierCategory.get({id: id}, function(result) {
                $scope.supplierCategory = result;
                $('#deleteSupplierCategoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            SupplierCategory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSupplierCategoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            SupplierCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.supplierCategorys = result;
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
            $scope.supplierCategory = {
                code: null,
                name: null,
                id: null
            };
        };
    });
