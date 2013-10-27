'use strict'

angular.module('muse.controllers.dashboard', ['muse.services'])

.controller('DashboardController', ['$scope', 'Project', ($scope, Project) ->
    console?.log "DashboardController function test"
    $scope.projects = Project.query()
])
