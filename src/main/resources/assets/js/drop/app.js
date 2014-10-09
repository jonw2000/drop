'use strict';

var dropApp = angular.module('dropApp', ['ngRoute', 'dropControllers', 'dropServices']);

dropApp.config(function($routeProvider) {
    $routeProvider.
        when('/', {
            templateUrl: 'partials/main.html',
            controller: 'DropCtrl'
        }).
        otherwise({
            redirectTo: '/'
        });
});