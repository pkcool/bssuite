'use strict';

angular.module('bssuiteApp')
    .controller('SupplierDetailController', function ($scope, $rootScope, $stateParams, entity, Supplier, SupplierCategory, Contact) {
        $scope.supplier = entity;
        $scope.load = function (id) {
            Supplier.get({id: id}, function(result) {
                $scope.supplier = result;
            });
        };
        $rootScope.$on('bssuiteApp:supplierUpdate', function(event, result) {
            $scope.supplier = result;
        });
    });
