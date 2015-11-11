'use strict';

describe('Bookmark Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockBookmark, MockStaff;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockBookmark = jasmine.createSpy('MockBookmark');
        MockStaff = jasmine.createSpy('MockStaff');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Bookmark': MockBookmark,
            'Staff': MockStaff
        };
        createController = function() {
            $injector.get('$controller')("BookmarkDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:bookmarkUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
