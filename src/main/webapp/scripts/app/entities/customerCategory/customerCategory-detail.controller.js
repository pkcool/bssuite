'use strict';

angular.module('bssuiteApp')
    .controller('CustomerCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, CustomerCategory) {
        $scope.customerCategory = entity;
        $scope.load = function (id) {
            CustomerCategory.get({id: id}, function(result) {
                $scope.customerCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:customerCategoryUpdate', function(event, result) {
            $scope.customerCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
