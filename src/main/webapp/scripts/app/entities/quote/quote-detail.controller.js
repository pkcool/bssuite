'use strict';

angular.module('bssuiteApp')
    .controller('QuoteDetailController', function ($scope, $rootScope, $stateParams, entity, Quote, Customer, Contact, Store, Staff, QuoteLineItem) {
        $scope.quote = entity;
        $scope.load = function (id) {
            Quote.get({id: id}, function(result) {
                $scope.quote = result;
            });
        };
        var unsubscribe = $rootScope.$on('bssuiteApp:quoteUpdate', function(event, result) {
            $scope.quote = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
