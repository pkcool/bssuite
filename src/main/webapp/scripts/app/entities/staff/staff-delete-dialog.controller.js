'use strict';

angular.module('bssuiteApp')
	.controller('StaffDeleteController', function($scope, $uibModalInstance, entity, Staff) {

        $scope.staff = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Staff.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
