'use strict';

describe('ProductActivityAudit Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProductActivityAudit, MockStaff, MockProduct;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProductActivityAudit = jasmine.createSpy('MockProductActivityAudit');
        MockStaff = jasmine.createSpy('MockStaff');
        MockProduct = jasmine.createSpy('MockProduct');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProductActivityAudit': MockProductActivityAudit,
            'Staff': MockStaff,
            'Product': MockProduct
        };
        createController = function() {
            $injector.get('$controller')("ProductActivityAuditDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:productActivityAuditUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
