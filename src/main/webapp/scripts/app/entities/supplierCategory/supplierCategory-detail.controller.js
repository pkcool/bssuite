'use strict';

angular.module('bssuiteApp')
    .controller('SupplierCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, SupplierCategory) {
        $scope.supplierCategory = entity;
        $scope.load = function (id) {
            SupplierCategory.get({id: id}, function(result) {
                $scope.supplierCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:supplierCategoryUpdate', function(event, result) {
            $scope.supplierCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
