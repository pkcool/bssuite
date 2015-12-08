'use strict';

angular.module('bssuiteApp').controller('QuoteDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Quote', 'Customer', 'Contact', 'Store', 'Staff', 'QuoteLineItem',
        function($scope, $stateParams, $uibModalInstance, entity, Quote, Customer, Contact, Store, Staff, QuoteLineItem) {

        $scope.quote = entity;
        $scope.customers = Customer.query();
        $scope.contacts = Contact.query();
        $scope.stores = Store.query();
        $scope.staffs = Staff.query();
        $scope.quotelineitems = QuoteLineItem.query();
        $scope.load = function(id) {
            Quote.get({id : id}, function(result) {
                $scope.quote = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bssuiteApp:quoteUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.quote.id != null) {
                Quote.update($scope.quote, onSaveSuccess, onSaveError);
            } else {
                Quote.save($scope.quote, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
