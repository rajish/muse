'use strict'

angular.module('muse.controllers', [])

.controller 'NavbarController', ($scope) ->
    $scope.loggedIn = true
    $scope.projects =[
        name: 'project A',
    ,
        name: 'project B',
    ]

.controller 'ProjectController', ($scope) ->
    console?.log "ProjectController function"

.controller 'SessionController', ($scope) ->
    console?.log "SessionController function"
