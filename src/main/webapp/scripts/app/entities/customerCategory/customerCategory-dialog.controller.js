'use strict';

angular.module('bssuiteApp').controller('CustomerCategoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCategory',
        function($scope, $stateParams, $uibModalInstance, entity, CustomerCategory) {

        $scope.customerCategory = entity;
        $scope.load = function(id) {
            CustomerCategory.get({id : id}, function(result) {
                $scope.customerCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:customerCategoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.customerCategory.id != null) {
                CustomerCategory.update($scope.customerCategory, onSaveSuccess, onSaveError);
            } else {
                CustomerCategory.save($scope.customerCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
