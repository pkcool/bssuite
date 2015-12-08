'use strict';

angular.module('bssuiteApp')
    .controller('GoodsReceivedAuditController', function ($scope, $state, GoodsReceivedAudit, GoodsReceivedAuditSearch, ParseLinks) {

        $scope.goodsReceivedAudits = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            GoodsReceivedAudit.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.goodsReceivedAudits = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            GoodsReceivedAuditSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.goodsReceivedAudits = result;
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
            $scope.goodsReceivedAudit = {
                receivedOn: null,
                txnNumber: null,
                typeReceipt: null,
                qtyReceived: null,
                id: null
            };
        };
    });
