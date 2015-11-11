'use strict';

describe('SalesOrder Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSalesOrder, MockCustomer, MockContact, MockStore, MockCarrier, MockStaff, MockPromotion, MockSalesOrderLineItem;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSalesOrder = jasmine.createSpy('MockSalesOrder');
        MockCustomer = jasmine.createSpy('MockCustomer');
        MockContact = jasmine.createSpy('MockContact');
        MockStore = jasmine.createSpy('MockStore');
        MockCarrier = jasmine.createSpy('MockCarrier');
        MockStaff = jasmine.createSpy('MockStaff');
        MockPromotion = jasmine.createSpy('MockPromotion');
        MockSalesOrderLineItem = jasmine.createSpy('MockSalesOrderLineItem');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SalesOrder': MockSalesOrder,
            'Customer': MockCustomer,
            'Contact': MockContact,
            'Store': MockStore,
            'Carrier': MockCarrier,
            'Staff': MockStaff,
            'Promotion': MockPromotion,
            'SalesOrderLineItem': MockSalesOrderLineItem
        };
        createController = function() {
            $injector.get('$controller')("SalesOrderDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:salesOrderUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
