'use strict';

angular.module('bssuiteApp')
    .factory('Product', function ($resource, DateUtils) {
        return $resource('api/products/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateFirstSale = DateUtils.convertLocaleDateFromServer(data.dateFirstSale);
                    data.dateLastSale = DateUtils.convertLocaleDateFromServer(data.dateLastSale);
                    data.dateFirstOrder = DateUtils.convertLocaleDateFromServer(data.dateFirstOrder);
                    data.dateCreated = DateUtils.convertLocaleDateFromServer(data.dateCreated);
                    data.dateLastDelivery = DateUtils.convertLocaleDateFromServer(data.dateLastDelivery);
                    data.dateNextDelivery = DateUtils.convertLocaleDateFromServer(data.dateNextDelivery);
                    data.dateLastTransfer = DateUtils.convertLocaleDateFromServer(data.dateLastTransfer);
                    data.dateLastOrder = DateUtils.convertLocaleDateFromServer(data.dateLastOrder);
                    data.dateLastStocktake = DateUtils.convertLocaleDateFromServer(data.dateLastStocktake);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateFirstSale = DateUtils.convertLocaleDateToServer(data.dateFirstSale);
                    data.dateLastSale = DateUtils.convertLocaleDateToServer(data.dateLastSale);
                    data.dateFirstOrder = DateUtils.convertLocaleDateToServer(data.dateFirstOrder);
                    data.dateCreated = DateUtils.convertLocaleDateToServer(data.dateCreated);
                    data.dateLastDelivery = DateUtils.convertLocaleDateToServer(data.dateLastDelivery);
                    data.dateNextDelivery = DateUtils.convertLocaleDateToServer(data.dateNextDelivery);
                    data.dateLastTransfer = DateUtils.convertLocaleDateToServer(data.dateLastTransfer);
                    data.dateLastOrder = DateUtils.convertLocaleDateToServer(data.dateLastOrder);
                    data.dateLastStocktake = DateUtils.convertLocaleDateToServer(data.dateLastStocktake);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateFirstSale = DateUtils.convertLocaleDateToServer(data.dateFirstSale);
                    data.dateLastSale = DateUtils.convertLocaleDateToServer(data.dateLastSale);
                    data.dateFirstOrder = DateUtils.convertLocaleDateToServer(data.dateFirstOrder);
                    data.dateCreated = DateUtils.convertLocaleDateToServer(data.dateCreated);
                    data.dateLastDelivery = DateUtils.convertLocaleDateToServer(data.dateLastDelivery);
                    data.dateNextDelivery = DateUtils.convertLocaleDateToServer(data.dateNextDelivery);
                    data.dateLastTransfer = DateUtils.convertLocaleDateToServer(data.dateLastTransfer);
                    data.dateLastOrder = DateUtils.convertLocaleDateToServer(data.dateLastOrder);
                    data.dateLastStocktake = DateUtils.convertLocaleDateToServer(data.dateLastStocktake);
                    return angular.toJson(data);
                }
            }
        });
    });
