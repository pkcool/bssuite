'use strict';

angular.module('bssuiteApp')
    .controller('StaffController', function ($scope, Staff, StaffSearch, ParseLinks) {
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

        $scope.delete = function (id) {
            Staff.get({id: id}, function(result) {
                $scope.staff = result;
                $('#deleteStaffConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Staff.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStaffConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
