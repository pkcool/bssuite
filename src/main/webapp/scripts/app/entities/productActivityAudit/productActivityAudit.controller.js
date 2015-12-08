'use strict';

angular.module('bssuiteApp')
    .controller('ProductActivityAuditController', function ($scope, $state, ProductActivityAudit, ProductActivityAuditSearch, ParseLinks) {

        $scope.productActivityAudits = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ProductActivityAudit.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
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
