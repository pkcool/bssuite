'use strict';

describe('SupplierDiscountRule Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSupplierDiscountRule;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSupplierDiscountRule = jasmine.createSpy('MockSupplierDiscountRule');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SupplierDiscountRule': MockSupplierDiscountRule
        };
        createController = function() {
            $injector.get('$controller')("SupplierDiscountRuleDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:supplierDiscountRuleUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
