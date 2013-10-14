'use strict'

angular.module( 'muse', [
    'ngRoute',
    'muse.controllers',
    'muse.directives',
    'muse.filters',
    'muse.services'])
.config(['$routeProvider', ($routeProvider) ->
        $routeProvider
            .when("/",
                templateUrl: "partials/index"
                controller: "ProjectController"
            )
            .when("/login",
                templateUrl: "partials/login"
                contoller: "SessionController"
            )
            .when("/project/:projectId",
                templateUrl: "partials/project"
                controller: "ProjectController"
            )
            .otherwise(redirectTo: "/")

        # $locationProvider.html5Mode true
])
