'use strict';

describe('Promotion Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPromotion, MockStore;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPromotion = jasmine.createSpy('MockPromotion');
        MockStore = jasmine.createSpy('MockStore');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Promotion': MockPromotion,
            'Store': MockStore
        };
        createController = function() {
            $injector.get('$controller')("PromotionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:promotionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
