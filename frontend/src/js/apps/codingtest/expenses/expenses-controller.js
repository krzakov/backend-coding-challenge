"use strict";

/******************************************************************************************

Expenses controller

******************************************************************************************/

var app = angular.module("expenses.controller", []);

app.controller("ctrlExpenses", ["$rootScope", "$scope", "config", "restalchemy", "$http", "notifications", function ExpensesCtrl($rootScope, $scope, $config, $restalchemy, $http, $notifications) {
	// Update the headings
	$rootScope.mainTitle = "Expenses";
	$rootScope.mainHeading = "Expenses";

	// Update the tab sections
	$rootScope.selectTabSection("expenses", 0);

	var restExpenses = $restalchemy.init({ root: $config.apiroot }).at("expenses");

	var vatRate = 0.20;

	$scope.currencyOptions = {
	    currencyServiceUrl: "//api.fixer.io/latest",
        currenciesCodes : ["EUR","GBP"],
        defaultCurrencyCode : "GBP"
	};

	$scope.selectedCurrencyCode = $scope.currencyOptions.defaultCurrencyCode;

	$scope.dateOptions = {
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy"
	};

	var loadExpenses = function() {
		// Retrieve a list of expenses via REST
		restExpenses.get().then(function(expenses) {
			$scope.expenses = expenses;
		}).error(function () {
            $notifications.show("Connection error", "Expenses cannot be loaded", "error", 5000);
        });
	};

	var isNumeric = function isNumeric(n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
    };

    var round = function (float) {
		return Math.round(float * 100) / 100;
    };

	$scope.saveExpense = function() {

		if ($scope.expensesform.$valid) {

            if($scope.selectedCurrencyCode !== "GBP"){
                $scope.newExpense.amount = round($scope.newExpense.amount * $scope.baseToGbp);
            }

			// Post the expense via REST
			restExpenses.post($scope.newExpense).then(function() {
				// Reload new expenses list
                $notifications.show("SUCCESS", "expense has been saved", "ok", 3000);
                $scope.clearExpense();
				loadExpenses();
			}).error(function () {
                $notifications.show("ERROR", "problem occured during saving Expense ", "error", 3000);
            });
		}
	};

	$scope.clearExpense = function() {
		$scope.newExpense = {};
		$scope.vat = null;
	};

    $scope.getExchangeRates = function (base) {
        $http({
            url: $scope.currencyOptions.currencyServiceUrl,
            method: "GET",
            data: [],
            params: {base: base, symbols: $scope.currencyOptions.defaultCurrencyCode}
        }).success(function (response) {
            $scope.baseToGbp = JSON.stringify(response['rates'][$scope.currencyOptions.defaultCurrencyCode]);
        }).error(function () {
            $notifications.show("Connection issue", "A problem occurred reading currency data from the 3rd party server ", "error", 5000);
        });
    };

    $scope.recalculateVat = function () {
        var input = $scope.newExpense.amount;

        if (isNumeric(input)) {
        	var result = input - (input / (1 + vatRate)); //rocket science
            $scope.vat = round(result)
        } else {
            $notifications.show("Formatting", "e.g. 123456.78", "error", 2000);
        }
    };

	// Initialise scope variables
	loadExpenses();
	$scope.clearExpense();
	$scope.getExchangeRates("EUR");
}]);
