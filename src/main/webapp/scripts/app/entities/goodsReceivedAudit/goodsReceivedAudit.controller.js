'use strict';

angular.module('bssuiteApp')
    .controller('GoodsReceivedAuditController', function ($scope, $state, $modal, GoodsReceivedAudit, GoodsReceivedAuditSearch, ParseLinks) {
      
        $scope.goodsReceivedAudits = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            GoodsReceivedAudit.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
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
