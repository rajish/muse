'use strict'

angular.module('muse', ['ngRoute', 'navbar'])

.config(['$routeProvider', '$locationProvider', ($routeProvider, $locationProvider) ->
    $routeProvider
    .when(
        '/',
        templateUrl: 'ui/views/welcome.html'
    )
    .otherwise(
        redirectTo: '/'
    )

    $locationProvider.html5Mode(true)
])
