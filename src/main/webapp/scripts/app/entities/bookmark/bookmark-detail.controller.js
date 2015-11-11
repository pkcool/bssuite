'use strict';

angular.module('bssuiteApp')
    .controller('BookmarkDetailController', function ($scope, $rootScope, $stateParams, entity, Bookmark, Staff) {
        $scope.bookmark = entity;
        $scope.load = function (id) {
            Bookmark.get({id: id}, function(result) {
                $scope.bookmark = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:bookmarkUpdate', function(event, result) {
            $scope.bookmark = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
