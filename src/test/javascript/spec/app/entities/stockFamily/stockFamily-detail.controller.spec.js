'use strict';

describe('StockFamily Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockStockFamily, MockPriceScale;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockStockFamily = jasmine.createSpy('MockStockFamily');
        MockPriceScale = jasmine.createSpy('MockPriceScale');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'StockFamily': MockStockFamily,
            'PriceScale': MockPriceScale
        };
        createController = function() {
            $injector.get('$controller')("StockFamilyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:stockFamilyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
