'use strict';

describe('PurchaseOrderLineItem Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPurchaseOrderLineItem, MockPurchaseOrder, MockProduct, MockTaxTable;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPurchaseOrderLineItem = jasmine.createSpy('MockPurchaseOrderLineItem');
        MockPurchaseOrder = jasmine.createSpy('MockPurchaseOrder');
        MockProduct = jasmine.createSpy('MockProduct');
        MockTaxTable = jasmine.createSpy('MockTaxTable');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PurchaseOrderLineItem': MockPurchaseOrderLineItem,
            'PurchaseOrder': MockPurchaseOrder,
            'Product': MockProduct,
            'TaxTable': MockTaxTable
        };
        createController = function() {
            $injector.get('$controller')("PurchaseOrderLineItemDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:purchaseOrderLineItemUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
