'use strict';

describe('RelatedProduct Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockRelatedProduct, MockProduct, MockProductRelationCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockRelatedProduct = jasmine.createSpy('MockRelatedProduct');
        MockProduct = jasmine.createSpy('MockProduct');
        MockProductRelationCategory = jasmine.createSpy('MockProductRelationCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'RelatedProduct': MockRelatedProduct,
            'Product': MockProduct,
            'ProductRelationCategory': MockProductRelationCategory
        };
        createController = function() {
            $injector.get('$controller')("RelatedProductDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'bssuiteApp:relatedProductUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
