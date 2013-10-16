'use strict'

angular.module( 'muse', [
    'ngRoute',
    'muse.controllers',
    'muse.directives',
    'muse.filters',
    'muse.services'])
.config(['$routeProvider', '$locationProvider', ($routeProvider, $locationProvider) ->
    $routeProvider
        .when("/",
            templateUrl: "partials/index"
            controller: "IndexController"
        )
        .when("/login",
            templateUrl: "partials/login"
            contoller: "SessionController"
        )
        .when("/project/:projectId",
            templateUrl: "partials/project"
            controller: "ProjectController"
        )
        .when("/nav",
            templateUrl: "partials/navbar"
            contoller: "NavbarController"
        )
        .otherwise(redirectTo: "/")

    $locationProvider.html5Mode true
])
