'use strict';

angular.module('bssuiteApp')
    .controller('ContactController', function ($scope, Contact, ContactSearch, ParseLinks) {
        $scope.contacts = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Contact.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.contacts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Contact.get({id: id}, function(result) {
                $scope.contact = result;
                $('#deleteContactConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Contact.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteContactConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
