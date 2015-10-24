'use strict';

describe('StockGroup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockStockGroup, MockStockFamily, MockPriceScale, MockTaxTable;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockStockGroup = jasmine.createSpy('MockStockGroup');
        MockStockFamily = jasmine.createSpy('MockStockFamily');
        MockPriceScale = jasmine.createSpy('MockPriceScale');
        MockTaxTable = jasmine.createSpy('MockTaxTable');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'StockGroup': MockStockGroup,
            'StockFamily': MockStockFamily,
            'PriceScale': MockPriceScale,
            'TaxTable': MockTaxTable
        };
        createController = function() {
            $injector.get('$controller')("StockGroupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:stockGroupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
