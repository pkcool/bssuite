'use strict';

describe('Staff Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockStaff, MockStore;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockStaff = jasmine.createSpy('MockStaff');
        MockStore = jasmine.createSpy('MockStore');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Staff': MockStaff,
            'Store': MockStore
        };
        createController = function() {
            $injector.get('$controller')("StaffDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:staffUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
