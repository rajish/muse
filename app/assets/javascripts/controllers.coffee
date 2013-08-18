angular.module('muse.controllers', [])

.controller 'NavbarController', ($scope) ->
    $scope.loggedIn = true

.controller 'ProjectController', ($scope) ->
    console?.log "ProjectController"                                            #doesn't work... why?

ProjectController = ($scope, $routeParams, $http) ->
    console?.log "ProjectController"
