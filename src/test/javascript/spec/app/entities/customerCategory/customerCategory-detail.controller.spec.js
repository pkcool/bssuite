'use strict';

describe('CustomerCategory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCustomerCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCustomerCategory = jasmine.createSpy('MockCustomerCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CustomerCategory': MockCustomerCategory
        };
        createController = function() {
            $injector.get('$controller')("CustomerCategoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:customerCategoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
