'use strict';

describe('TxnActivityAudit Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTxnActivityAudit, MockStaff;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTxnActivityAudit = jasmine.createSpy('MockTxnActivityAudit');
        MockStaff = jasmine.createSpy('MockStaff');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TxnActivityAudit': MockTxnActivityAudit,
            'Staff': MockStaff
        };
        createController = function() {
            $injector.get('$controller')("TxnActivityAuditDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:txnActivityAuditUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
