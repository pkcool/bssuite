'use strict';

describe('QuoteLineItem Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockQuoteLineItem, MockQuote, MockProduct, MockTaxTable;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockQuoteLineItem = jasmine.createSpy('MockQuoteLineItem');
        MockQuote = jasmine.createSpy('MockQuote');
        MockProduct = jasmine.createSpy('MockProduct');
        MockTaxTable = jasmine.createSpy('MockTaxTable');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'QuoteLineItem': MockQuoteLineItem,
            'Quote': MockQuote,
            'Product': MockProduct,
            'TaxTable': MockTaxTable
        };
        createController = function() {
            $injector.get('$controller')("QuoteLineItemDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:quoteLineItemUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
