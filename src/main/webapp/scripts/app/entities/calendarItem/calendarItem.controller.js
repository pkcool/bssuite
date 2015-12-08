'use strict';

angular.module('bssuiteApp')
    .controller('CalendarItemController', function ($scope, $state, CalendarItem, CalendarItemSearch, ParseLinks) {

        $scope.calendarItems = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            CalendarItem.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.calendarItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CalendarItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.calendarItems = result;
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
            $scope.calendarItem = {
                title: null,
                startedOn: null,
                endedOn: null,
                isAllDayEvent: null,
                isRemainderEnabled: null,
                remainderTime: null,
                webUrl: null,
                isEditable: null,
                alarmType: null,
                id: null
            };
        };
    });
