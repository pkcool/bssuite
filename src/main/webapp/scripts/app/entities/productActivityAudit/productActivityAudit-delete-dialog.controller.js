'use strict';

angular.module('bssuiteApp')
	.controller('ProductActivityAuditDeleteController', function($scope, $modalInstance, entity, ProductActivityAudit) {

        $scope.productActivityAudit = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProductActivityAudit.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });