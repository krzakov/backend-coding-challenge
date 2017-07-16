"use strict";

/******************************************************************************************

 Exchange rates service

 This service handles the calculating exchange rate for provided currencies.
 Service uses external 3rd party API from fixer.io

 Usage:

 ******************************************************************************************/

var app = angular.module("alchemytec.exchangeRates", []);

app.factory("exchangeRates", ["$http", function ($http) {

    var currencyServiceUrl = "//api.fixer.io/latest";

    return {
        getExchangeRates: function (baseCurrencyCode, resultCurrencyCode) {
            return $http({
                url: currencyServiceUrl,
                method: "GET",
                data: [],
                params: {base: baseCurrencyCode, symbols: resultCurrencyCode}
            });
        }
    };
}]);
