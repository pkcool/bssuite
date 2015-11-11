'use strict';

angular.module('bssuiteApp')
    .controller('ProductActivityAuditDetailController', function ($scope, $rootScope, $stateParams, entity, ProductActivityAudit, Staff, Product) {
        $scope.productActivityAudit = entity;
        $scope.load = function (id) {
            ProductActivityAudit.get({id: id}, function(result) {
                $scope.productActivityAudit = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:productActivityAuditUpdate', function(event, result) {
            $scope.productActivityAudit = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
