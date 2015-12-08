'use strict';

angular.module('bssuiteApp')
    .controller('TxnActivityAuditController', function ($scope, $state, TxnActivityAudit, TxnActivityAuditSearch, ParseLinks) {

        $scope.txnActivityAudits = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            TxnActivityAudit.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.txnActivityAudits = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            TxnActivityAuditSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.txnActivityAudits = result;
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
            $scope.txnActivityAudit = {
                editedOn: null,
                txnNumber: null,
                txnType: null,
                txnAmount: null,
                bankAcc: null,
                editType: null,
                id: null
            };
        };
    });
