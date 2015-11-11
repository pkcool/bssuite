'use strict';

angular.module('bssuiteApp')
	.controller('BackOrderLineItemDeleteController', function($scope, $modalInstance, entity, BackOrderLineItem) {

        $scope.backOrderLineItem = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BackOrderLineItem.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });