'use strict';

angular.module('bssuiteApp')
    .controller('StoreController', function ($scope, $state, Store, StoreSearch, ParseLinks) {

        $scope.stores = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Store.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.stores = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


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
                inBusinessSince: null,
                isArchived: null,
                id: null
            };
        };
    });
