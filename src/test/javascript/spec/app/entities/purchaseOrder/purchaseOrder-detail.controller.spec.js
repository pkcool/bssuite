'use strict';

describe('PurchaseOrder Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPurchaseOrder, MockSupplier, MockContact, MockStore, MockSalesOrder, MockStaff, MockPurchaseOrderLineItem;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPurchaseOrder = jasmine.createSpy('MockPurchaseOrder');
        MockSupplier = jasmine.createSpy('MockSupplier');
        MockContact = jasmine.createSpy('MockContact');
        MockStore = jasmine.createSpy('MockStore');
        MockSalesOrder = jasmine.createSpy('MockSalesOrder');
        MockStaff = jasmine.createSpy('MockStaff');
        MockPurchaseOrderLineItem = jasmine.createSpy('MockPurchaseOrderLineItem');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PurchaseOrder': MockPurchaseOrder,
            'Supplier': MockSupplier,
            'Contact': MockContact,
            'Store': MockStore,
            'SalesOrder': MockSalesOrder,
            'Staff': MockStaff,
            'PurchaseOrderLineItem': MockPurchaseOrderLineItem
        };
        createController = function() {
            $injector.get('$controller')("PurchaseOrderDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:purchaseOrderUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
