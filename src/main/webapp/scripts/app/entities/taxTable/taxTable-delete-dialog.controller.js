'use strict';

angular.module('bssuiteApp')
	.controller('TaxTableDeleteController', function($scope, $modalInstance, entity, TaxTable) {

        $scope.taxTable = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TaxTable.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });