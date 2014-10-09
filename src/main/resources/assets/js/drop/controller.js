'use strict';

var dropControllers = angular.module('dropControllers', []);

dropControllers.controller('DropCtrl', function($scope, Data) {
    Data.query(function(data) {
        new AreaGraph().draw('svg', data);
    })
});