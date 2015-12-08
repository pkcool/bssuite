'use strict';

angular.module('bssuiteApp')
	.controller('BackOrderLineItemDeleteController', function($scope, $uibModalInstance, entity, BackOrderLineItem) {

        $scope.backOrderLineItem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BackOrderLineItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
