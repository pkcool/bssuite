'use strict';

describe('BackOrderLineItem Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockBackOrderLineItem, MockSalesOrderLineItem;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockBackOrderLineItem = jasmine.createSpy('MockBackOrderLineItem');
        MockSalesOrderLineItem = jasmine.createSpy('MockSalesOrderLineItem');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'BackOrderLineItem': MockBackOrderLineItem,
            'SalesOrderLineItem': MockSalesOrderLineItem
        };
        createController = function() {
            $injector.get('$controller')("BackOrderLineItemDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:backOrderLineItemUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
