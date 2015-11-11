'use strict';

angular.module('bssuiteApp')
    .controller('TaxTableDetailController', function ($scope, $rootScope, $stateParams, entity, TaxTable) {
        $scope.taxTable = entity;
        $scope.load = function (id) {
            TaxTable.get({id: id}, function(result) {
                $scope.taxTable = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:taxTableUpdate', function(event, result) {
            $scope.taxTable = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
