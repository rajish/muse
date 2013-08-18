'use strict'

angular.module 'muse', ['ngRoute', 'muse.controllers'], ($routeProvider, $locationProvider) ->
    $routeProvider.when "/project/:projectId",
        templateUrl: "partials/project.html",
        controller: ProjectController,
        controllerAs: "proj"

    $locationProvider.html5Mode true
