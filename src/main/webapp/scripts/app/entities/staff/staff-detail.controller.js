'use strict';

angular.module('bssuiteApp')
    .controller('StaffDetailController', function ($scope, $rootScope, $stateParams, entity, Staff, Store) {
        $scope.staff = entity;
        $scope.load = function (id) {
            Staff.get({id: id}, function(result) {
                $scope.staff = result;
            });
        };
        $rootScope.$on('bssuiteApp:staffUpdate', function(event, result) {
            $scope.staff = result;
        });
    });
