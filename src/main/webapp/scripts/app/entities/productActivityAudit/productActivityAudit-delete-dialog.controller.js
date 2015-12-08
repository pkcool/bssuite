'use strict';

angular.module('bssuiteApp')
	.controller('ProductActivityAuditDeleteController', function($scope, $uibModalInstance, entity, ProductActivityAudit) {

        $scope.productActivityAudit = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProductActivityAudit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
