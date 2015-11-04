'use strict';

angular.module('bssuiteApp')
    .controller('StoreManagementController', function ($scope, Store, StoreSearch, ParseLinks) {
        $scope.stores = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Store.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.stores = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Store.get({id: id}, function(result) {
                $scope.store = result;
                $('#deleteStoreConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Store.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStoreConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            StoreSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.stores = result;
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
            $scope.store = {
                code: null,
                name: null,
                address1: null,
                address2: null,
                suburb: null,
                state: null,
                postcode: null,
                country: null,
                phone: null,
                fax: null,
                email: null,
                webUrl: null,
                inBisinessSince: null,
                isArchived: null,
                id: null
            };
        };
    });
