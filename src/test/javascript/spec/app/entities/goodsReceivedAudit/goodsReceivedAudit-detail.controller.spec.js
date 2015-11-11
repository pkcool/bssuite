'use strict';

describe('GoodsReceivedAudit Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockGoodsReceivedAudit, MockStaff, MockSupplier, MockPurchaseOrder, MockProduct;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockGoodsReceivedAudit = jasmine.createSpy('MockGoodsReceivedAudit');
        MockStaff = jasmine.createSpy('MockStaff');
        MockSupplier = jasmine.createSpy('MockSupplier');
        MockPurchaseOrder = jasmine.createSpy('MockPurchaseOrder');
        MockProduct = jasmine.createSpy('MockProduct');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'GoodsReceivedAudit': MockGoodsReceivedAudit,
            'Staff': MockStaff,
            'Supplier': MockSupplier,
            'PurchaseOrder': MockPurchaseOrder,
            'Product': MockProduct
        };
        createController = function() {
            $injector.get('$controller')("GoodsReceivedAuditDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:goodsReceivedAuditUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
