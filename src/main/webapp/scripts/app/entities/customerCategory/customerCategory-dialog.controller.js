'use strict';

angular.module('bssuiteApp').controller('CustomerCategoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CustomerCategory',
        function($scope, $stateParams, $modalInstance, entity, CustomerCategory) {

        $scope.customerCategory = entity;
        $scope.load = function(id) {
            CustomerCategory.get({id : id}, function(result) {
                $scope.customerCategory = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:customerCategoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.customerCategory.id != null) {
                CustomerCategory.update($scope.customerCategory, onSaveFinished);
            } else {
                CustomerCategory.save($scope.customerCategory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
