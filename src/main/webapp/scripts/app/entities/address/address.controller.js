'use strict';

angular.module('bssuiteApp')
    .controller('AddressController', function ($scope, $state, $modal, Address, AddressSearch, ParseLinks) {
      
        $scope.addresss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Address.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.addresss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            AddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.addresss = result;
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
            $scope.address = {
                addressLine1: null,
                addressLine2: null,
                suburb: null,
                state: null,
                postcode: null,
                country: null,
                id: null
            };
        };
    });
