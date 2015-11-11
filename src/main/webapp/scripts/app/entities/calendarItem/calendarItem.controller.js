'use strict';

angular.module('bssuiteApp')
    .controller('CalendarItemController', function ($scope, $state, $modal, CalendarItem, CalendarItemSearch, ParseLinks) {
      
        $scope.calendarItems = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CalendarItem.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
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
