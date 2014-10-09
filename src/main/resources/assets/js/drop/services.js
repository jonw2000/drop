'use strict';

var dropServices = angular.module('dropServices', ['ngResource']);

dropServices.factory('Data', function($resource){
    return $resource('/rest/data', {}, {
        query: {method:'GET', isArray:true}
    });
});