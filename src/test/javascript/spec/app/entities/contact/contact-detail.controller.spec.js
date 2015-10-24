'use strict';

describe('Contact Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockContact, MockAddress;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockContact = jasmine.createSpy('MockContact');
        MockAddress = jasmine.createSpy('MockAddress');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Contact': MockContact,
            'Address': MockAddress
        };
        createController = function() {
            $injector.get('$controller')("ContactDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:contactUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
