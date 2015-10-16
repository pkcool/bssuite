'use strict';

angular.module('bssuiteApp').controller('QuoteDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Quote', 'Customer', 'Contact', 'Store', 'Staff', 'QuoteLineItem',
        function($scope, $stateParams, $modalInstance, entity, Quote, Customer, Contact, Store, Staff, QuoteLineItem) {

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

        var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:quoteUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.quote.id != null) {
                Quote.update($scope.quote, onSaveFinished);
            } else {
                Quote.save($scope.quote, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
