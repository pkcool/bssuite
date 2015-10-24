'use strict';

angular.module('bssuiteApp')
    .controller('SupplierDetailController', function ($scope, $rootScope, $stateParams, entity, Supplier, SupplierCategory, Contact) {
        $scope.supplier = entity;
        $scope.load = function (id) {
            Supplier.get({id: id}, function(result) {
                $scope.supplier = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:supplierUpdate', function(event, result) {
            $scope.supplier = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
