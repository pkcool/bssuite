'use strict';

angular.module('bssuiteApp')
    .controller('RelatedProductDetailController', function ($scope, $rootScope, $stateParams, entity, RelatedProduct, Product, ProductRelationCategory) {
        $scope.relatedProduct = entity;
        $scope.load = function (id) {
            RelatedProduct.get({id: id}, function(result) {
                $scope.relatedProduct = result;
            });
        };
        $rootScope.$on('bssuiteApp:relatedProductUpdate', function(event, result) {
            $scope.relatedProduct = result;
        });
    });
