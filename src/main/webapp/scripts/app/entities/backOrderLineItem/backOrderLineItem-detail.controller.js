'use strict';

angular.module('bssuiteApp')
    .controller('BackOrderLineItemDetailController', function ($scope, $rootScope, $stateParams, entity, BackOrderLineItem, SalesOrderLineItem) {
        $scope.backOrderLineItem = entity;
        $scope.load = function (id) {
            BackOrderLineItem.get({id: id}, function(result) {
                $scope.backOrderLineItem = result;
            });
        };
        $rootScope.$on('bssuiteApp:backOrderLineItemUpdate', function(event, result) {
            $scope.backOrderLineItem = result;
        });
    });
