'use strict';

describe('InvoiceLineItem Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInvoiceLineItem, MockInvoice, MockProduct, MockTaxTable;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInvoiceLineItem = jasmine.createSpy('MockInvoiceLineItem');
        MockInvoice = jasmine.createSpy('MockInvoice');
        MockProduct = jasmine.createSpy('MockProduct');
        MockTaxTable = jasmine.createSpy('MockTaxTable');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InvoiceLineItem': MockInvoiceLineItem,
            'Invoice': MockInvoice,
            'Product': MockProduct,
            'TaxTable': MockTaxTable
        };
        createController = function() {
            $injector.get('$controller')("InvoiceLineItemDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:invoiceLineItemUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
