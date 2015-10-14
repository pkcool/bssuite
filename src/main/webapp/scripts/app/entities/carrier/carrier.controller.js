'use strict';

angular.module('bssuiteApp')
    .controller('CarrierController', function ($scope, Carrier, CarrierSearch, ParseLinks) {
        $scope.carriers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Carrier.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.carriers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Carrier.get({id: id}, function(result) {
                $scope.carrier = result;
                $('#deleteCarrierConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Carrier.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCarrierConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
