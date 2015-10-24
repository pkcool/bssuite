'use strict';

describe('Quote Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockQuote, MockCustomer, MockContact, MockStore, MockStaff, MockQuoteLineItem;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockQuote = jasmine.createSpy('MockQuote');
        MockCustomer = jasmine.createSpy('MockCustomer');
        MockContact = jasmine.createSpy('MockContact');
        MockStore = jasmine.createSpy('MockStore');
        MockStaff = jasmine.createSpy('MockStaff');
        MockQuoteLineItem = jasmine.createSpy('MockQuoteLineItem');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Quote': MockQuote,
            'Customer': MockCustomer,
            'Contact': MockContact,
            'Store': MockStore,
            'Staff': MockStaff,
            'QuoteLineItem': MockQuoteLineItem
        };
        createController = function() {
            $injector.get('$controller')("QuoteDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:quoteUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
