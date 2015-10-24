'use strict';

describe('CustomerDiscountRule Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCustomerDiscountRule;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCustomerDiscountRule = jasmine.createSpy('MockCustomerDiscountRule');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CustomerDiscountRule': MockCustomerDiscountRule
        };
        createController = function() {
            $injector.get('$controller')("CustomerDiscountRuleDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:customerDiscountRuleUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
