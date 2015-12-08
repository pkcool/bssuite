'use strict';

angular.module('bssuiteApp')
    .controller('CarrierController', function ($scope, $state, Carrier, CarrierSearch, ParseLinks) {

        $scope.carriers = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Carrier.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.carriers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CarrierSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.carriers = result;
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
            $scope.carrier = {
                code: null,
                name: null,
                phone: null,
                mobile: null,
                accountNo: null,
                comment: null,
                isAvailableOnMonday: null,
                isAvailableOnTuesday: null,
                isAvailableOnWednesday: null,
                isAvailableOnThursday: null,
                isAvailableOnFriday: null,
                isAvailableOnSaturday: null,
                isAvailableOnSunday: null,
                docketRefNo: null,
                id: null
            };
        };
    });
