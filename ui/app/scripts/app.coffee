'use strict'

angular.module('muse', ['ngRoute', 'navbar'])

.config(['$routeProvider', '$locationProvider', ($routeProvider, $locationProvider) ->
    $routeProvider
    .when(
        '/',
        templateUrl: 'views/welcome.html'
    )
    .otherwise(
        redirectTo: '/'
    )
])
