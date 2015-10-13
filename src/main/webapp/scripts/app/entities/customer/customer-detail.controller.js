'use strict';

angular.module('bssuiteApp')
    .controller('CustomerDetailController', function ($scope, $rootScope, $stateParams, entity, Customer, CustomerCategory, Contact, Staff, Store) {
        $scope.customer = entity;
        $scope.load = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
            });
        };
        $rootScope.$on('bssuiteApp:customerUpdate', function(event, result) {
            $scope.customer = result;
        });
    });
