'use strict';

angular.module('bssuiteApp')
    .controller('BackOrderLineItemController', function ($scope, $state, $modal, BackOrderLineItem, BackOrderLineItemSearch, ParseLinks) {
      
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
