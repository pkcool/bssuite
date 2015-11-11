'use strict';

angular.module('bssuiteApp')
    .controller('StaffController', function ($scope, $state, $modal, Staff, StaffSearch, ParseLinks) {
      
        $scope.staffs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Staff.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
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
