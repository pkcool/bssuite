'use strict';

angular.module('bssuiteApp')
	.controller('CustomerCategoryDeleteController', function($scope, $modalInstance, entity, CustomerCategory) {

        $scope.customerCategory = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CustomerCategory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });