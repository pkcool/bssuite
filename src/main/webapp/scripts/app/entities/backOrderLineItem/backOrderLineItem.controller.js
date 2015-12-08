'use strict';

angular.module('bssuiteApp')
    .controller('BackOrderLineItemController', function ($scope, $state, BackOrderLineItem, BackOrderLineItemSearch, ParseLinks) {

        $scope.backOrderLineItems = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            BackOrderLineItem.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.backOrderLineItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


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
