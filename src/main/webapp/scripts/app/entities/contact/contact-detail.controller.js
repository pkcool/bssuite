'use strict';

angular.module('bssuiteApp')
    .controller('ContactDetailController', function ($scope, $rootScope, $stateParams, entity, Contact, Address) {
        $scope.contact = entity;
        $scope.load = function (id) {
            Contact.get({id: id}, function(result) {
                $scope.contact = result;
            });
        };
        $rootScope.$on('bssuiteApp:contactUpdate', function(event, result) {
            $scope.contact = result;
        });
    });
