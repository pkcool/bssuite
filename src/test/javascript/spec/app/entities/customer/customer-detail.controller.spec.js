'use strict';

describe('Customer Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCustomer, MockCustomerCategory, MockContact, MockStaff, MockStore;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCustomer = jasmine.createSpy('MockCustomer');
        MockCustomerCategory = jasmine.createSpy('MockCustomerCategory');
        MockContact = jasmine.createSpy('MockContact');
        MockStaff = jasmine.createSpy('MockStaff');
        MockStore = jasmine.createSpy('MockStore');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Customer': MockCustomer,
            'CustomerCategory': MockCustomerCategory,
            'Contact': MockContact,
            'Staff': MockStaff,
            'Store': MockStore
        };
        createController = function() {
            $injector.get('$controller')("CustomerDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:customerUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
