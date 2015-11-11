'use strict';

describe('Invoice Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInvoice, MockCustomer, MockContact, MockStore, MockCarrier, MockStaff, MockPromotion, MockInvoiceLineItem;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInvoice = jasmine.createSpy('MockInvoice');
        MockCustomer = jasmine.createSpy('MockCustomer');
        MockContact = jasmine.createSpy('MockContact');
        MockStore = jasmine.createSpy('MockStore');
        MockCarrier = jasmine.createSpy('MockCarrier');
        MockStaff = jasmine.createSpy('MockStaff');
        MockPromotion = jasmine.createSpy('MockPromotion');
        MockInvoiceLineItem = jasmine.createSpy('MockInvoiceLineItem');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Invoice': MockInvoice,
            'Customer': MockCustomer,
            'Contact': MockContact,
            'Store': MockStore,
            'Carrier': MockCarrier,
            'Staff': MockStaff,
            'Promotion': MockPromotion,
            'InvoiceLineItem': MockInvoiceLineItem
        };
        createController = function() {
            $injector.get('$controller')("InvoiceDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:invoiceUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
