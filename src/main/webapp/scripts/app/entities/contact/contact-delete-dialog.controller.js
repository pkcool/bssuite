'use strict';

angular.module('bssuiteApp')
	.controller('ContactDeleteController', function($scope, $uibModalInstance, entity, Contact) {

        $scope.contact = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Contact.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
