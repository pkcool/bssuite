'use strict';

angular.module('bssuiteApp').controller('BookmarkDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Bookmark', 'Staff',
        function($scope, $stateParams, $modalInstance, entity, Bookmark, Staff) {

        $scope.bookmark = entity;
        $scope.staffs = Staff.query();
        $scope.load = function(id) {
            Bookmark.get({id : id}, function(result) {
                $scope.bookmark = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:bookmarkUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bookmark.id != null) {
                Bookmark.update($scope.bookmark, onSaveSuccess, onSaveError);
            } else {
                Bookmark.save($scope.bookmark, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
