'use strict';

angular.module('bssuiteApp')
	.controller('ContactDeleteController', function($scope, $modalInstance, entity, Contact) {

        $scope.contact = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Contact.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });