'use strict';


angular.module('bssuiteApp')
    .controller('StoreManagementImportController', function ($scope, $parse, Store ) {
        $scope.csv = {
            content: null,
            header: true,
            headerVisible: true,
            separator: ',',
            separatorVisible: true,
            result: null,
            encoding: 'ISO-8859-1',
            encodingVisible: true,
        };

        $scope.shouldDisplayParsedValues = false;

        var _lastGoodResult = '';
        $scope.toPrettyJSON = function (json, tabWidth) {
            var objStr = JSON.stringify(json);
            var obj = null;
            try {
                obj = $parse(objStr)({});
            } catch(e){
                // eat $parse error
                return _lastGoodResult;
            }

            var result = JSON.stringify(obj, null, Number(tabWidth));
            _lastGoodResult = result;

            return result;
        };


       var onSaveFinished = function (result) {
            $scope.$emit('bssuiteApp:storeAdd', result);
        };

        $scope.save = function () {
            angular.forEach($scope.csv.result, function (value, key) {
               Store.save(value, onSaveFinished);
            });
        };
    });


