'use strict';

angular.module('bssuiteApp')
    .controller('ProductActivityAuditController', function ($scope, $state, $modal, ProductActivityAudit, ProductActivityAuditSearch, ParseLinks) {
      
        $scope.productActivityAudits = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            ProductActivityAudit.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.productActivityAudits = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProductActivityAuditSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.productActivityAudits = result;
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
            $scope.productActivityAudit = {
                createdOn: null,
                txnNumber: null,
                activityType: null,
                qtyTxn: null,
                qtyAdjusted: null,
                qtyStockOnHold: null,
                lineNo: null,
                txnAccountCode: null,
                id: null
            };
        };
    });
