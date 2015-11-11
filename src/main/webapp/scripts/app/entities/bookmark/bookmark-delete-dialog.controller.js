'use strict';

angular.module('bssuiteApp')
	.controller('BookmarkDeleteController', function($scope, $modalInstance, entity, Bookmark) {

        $scope.bookmark = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Bookmark.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });