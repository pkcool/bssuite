'use strict';

angular.module('bssuiteApp')
	.controller('StaffDeleteController', function($scope, $modalInstance, entity, Staff) {

        $scope.staff = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Staff.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });