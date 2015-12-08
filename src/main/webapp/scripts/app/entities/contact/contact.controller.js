'use strict';

angular.module('bssuiteApp')
    .controller('ContactController', function ($scope, $state, Contact, ContactSearch, ParseLinks) {

        $scope.contacts = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Contact.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.contacts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ContactSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.contacts = result;
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
            $scope.contact = {
                fistName: null,
                lastName: null,
                fullName: null,
                emailAddress: null,
                companyName: null,
                phonePrimary: null,
                phoneSecondary: null,
                fax: null,
                position: null,
                id: null
            };
        };
    });
