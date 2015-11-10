'use strict';

angular.module('bssuiteApp')
    .controller('BookmarkController', function ($scope, Bookmark, BookmarkSearch, ParseLinks) {
        $scope.bookmarks = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Bookmark.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bookmarks = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Bookmark.get({id: id}, function(result) {
                $scope.bookmark = result;
                $('#deleteBookmarkConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Bookmark.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBookmarkConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            BookmarkSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bookmarks = result;
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
            $scope.bookmark = {
                createdOn: null,
                txnNumber: null,
                bookmarkType: null,
                bookmarkArea: null,
                keyCode: null,
                title: null,
                lastEditedOn: null,
                openCount: null,
                id: null
            };
        };
    });
