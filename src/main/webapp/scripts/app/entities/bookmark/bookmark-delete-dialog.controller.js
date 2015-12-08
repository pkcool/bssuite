'use strict';

angular.module('bssuiteApp')
	.controller('BookmarkDeleteController', function($scope, $uibModalInstance, entity, Bookmark) {

        $scope.bookmark = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Bookmark.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
