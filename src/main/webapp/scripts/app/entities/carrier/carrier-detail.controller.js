'use strict';

angular.module('bssuiteApp')
    .controller('CarrierDetailController', function ($scope, $rootScope, $stateParams, entity, Carrier) {
        $scope.carrier = entity;
        $scope.load = function (id) {
            Carrier.get({id: id}, function(result) {
                $scope.carrier = result;
            });
        };
        $rootScope.$on('bssuiteApp:carrierUpdate', function(event, result) {
            $scope.carrier = result;
        });
    });
