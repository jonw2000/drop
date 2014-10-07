'use strict';

var dropApp = angular.module('dropApp', ['ngRoute']);

dropApp.config(function($routeProvider) {
    $routeProvider.
        when('/', {
            templateUrl: 'partials/main.html',
            controller: 'dropController'
        }).
        otherwise({
            redirectTo: '/'
        });
});