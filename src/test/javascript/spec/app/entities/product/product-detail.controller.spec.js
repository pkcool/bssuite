'use strict';

describe('Product Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProduct, MockStockGroup, MockSupplier, MockStore, MockTaxTable;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProduct = jasmine.createSpy('MockProduct');
        MockStockGroup = jasmine.createSpy('MockStockGroup');
        MockSupplier = jasmine.createSpy('MockSupplier');
        MockStore = jasmine.createSpy('MockStore');
        MockTaxTable = jasmine.createSpy('MockTaxTable');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Product': MockProduct,
            'StockGroup': MockStockGroup,
            'Supplier': MockSupplier,
            'Store': MockStore,
            'TaxTable': MockTaxTable
        };
        createController = function() {
            $injector.get('$controller')("ProductDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:productUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
