'use strict';

describe('SalesOrderLineItem Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSalesOrderLineItem, MockSalesOrder, MockProduct, MockTaxTable;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSalesOrderLineItem = jasmine.createSpy('MockSalesOrderLineItem');
        MockSalesOrder = jasmine.createSpy('MockSalesOrder');
        MockProduct = jasmine.createSpy('MockProduct');
        MockTaxTable = jasmine.createSpy('MockTaxTable');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SalesOrderLineItem': MockSalesOrderLineItem,
            'SalesOrder': MockSalesOrder,
            'Product': MockProduct,
            'TaxTable': MockTaxTable
        };
        createController = function() {
            $injector.get('$controller')("SalesOrderLineItemDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:salesOrderLineItemUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
