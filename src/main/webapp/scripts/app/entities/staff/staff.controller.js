'use strict';

angular.module('bssuiteApp')
    .controller('StaffController', function ($scope, $state, Staff, StaffSearch, ParseLinks) {

        $scope.staffs = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Staff.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.staffs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            StaffSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.staffs = result;
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
            $scope.staff = {
                code: null,
                name: null,
                comment: null,
                commission: null,
                occupation: null,
                workPhone: null,
                homePhone: null,
                workMobile: null,
                homeMobile: null,
                webEmail: null,
                homeEmail: null,
                birthday: null,
                isLockedToStore: null,
                isTechnical: null,
                id: null
            };
        };
    });
