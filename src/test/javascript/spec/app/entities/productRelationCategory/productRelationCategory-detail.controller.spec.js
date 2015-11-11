'use strict';

describe('ProductRelationCategory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProductRelationCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProductRelationCategory = jasmine.createSpy('MockProductRelationCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProductRelationCategory': MockProductRelationCategory
        };
        createController = function() {
            $injector.get('$controller')("ProductRelationCategoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:productRelationCategoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
