'use strict';

angular.module('bssuiteApp')
    .controller('ProductRelationCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, ProductRelationCategory) {
        $scope.productRelationCategory = entity;
        $scope.load = function (id) {
            ProductRelationCategory.get({id: id}, function(result) {
                $scope.productRelationCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:productRelationCategoryUpdate', function(event, result) {
            $scope.productRelationCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
