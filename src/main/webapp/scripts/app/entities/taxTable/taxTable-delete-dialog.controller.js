'use strict';

angular.module('bssuiteApp')
	.controller('TaxTableDeleteController', function($scope, $uibModalInstance, entity, TaxTable) {

        $scope.taxTable = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TaxTable.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
