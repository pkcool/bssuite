'use strict';

describe('CalendarItem Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCalendarItem, MockStaff;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCalendarItem = jasmine.createSpy('MockCalendarItem');
        MockStaff = jasmine.createSpy('MockStaff');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CalendarItem': MockCalendarItem,
            'Staff': MockStaff
        };
        createController = function() {
            $injector.get('$controller')("CalendarItemDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:calendarItemUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
