'use strict';

describe('Supplier Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSupplier, MockSupplierCategory, MockContact;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSupplier = jasmine.createSpy('MockSupplier');
        MockSupplierCategory = jasmine.createSpy('MockSupplierCategory');
        MockContact = jasmine.createSpy('MockContact');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Supplier': MockSupplier,
            'SupplierCategory': MockSupplierCategory,
            'Contact': MockContact
        };
        createController = function() {
            $injector.get('$controller')("SupplierDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:supplierUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
