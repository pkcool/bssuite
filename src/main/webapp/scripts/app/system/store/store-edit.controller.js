'use strict';


angular.module('bssuiteApp')
    .controller('StoreManagementEditController', function ($scope, $rootScope, $stateParams, entity, Store, formsCommon) {
        $scope.store = entity;
        $scope.countries = formsCommon.countries;
        $scope.load = function (id) {
            Store.get({id: id}, function(result) {
                $scope.store = result;
            });
        };
        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:storeUpdate', result);
        };

        $scope.save = function () {
            if ($scope.store.id != null) {
                Store.update($scope.store, onSaveFinished);
            } else {
                Store.save($scope.store, onSaveFinished);
            }
        };
    });
