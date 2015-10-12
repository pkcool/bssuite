'use strict';

angular.module('bssuiteApp')
    .controller('AddressDetailController', function ($scope, $rootScope, $stateParams, entity, Address) {
        $scope.address = entity;
        $scope.load = function (id) {
            Address.get({id: id}, function(result) {
                $scope.address = result;
            });
        };
        $rootScope.$on('bssuiteApp:addressUpdate', function(event, result) {
            $scope.address = result;
        });
    });
