'use strict';

angular.module('bssuiteApp')
    .controller('BackOrderLineItemController', function ($scope, BackOrderLineItem, BackOrderLineItemSearch, ParseLinks) {
        $scope.backOrderLineItems = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            BackOrderLineItem.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.backOrderLineItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            BackOrderLineItem.get({id: id}, function(result) {
                $scope.backOrderLineItem = result;
                $('#deleteBackOrderLineItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            BackOrderLineItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBackOrderLineItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            BackOrderLineItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.backOrderLineItems = result;
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
            $scope.backOrderLineItem = {
                isReadyToRelease: null,
                qtyAllocated: null,
                isMarkedForAutoPurchaseOrdering: null,
                isOnHold: null,
                allocatedDate: null,
                comment: null,
                isPicked: null,
                isMarked: null,
                id: null
            };
        };
    });
