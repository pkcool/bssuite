'use strict';

angular.module('bssuiteApp')
	.controller('CustomerCategoryDeleteController', function($scope, $uibModalInstance, entity, CustomerCategory) {

        $scope.customerCategory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CustomerCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
