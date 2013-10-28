'use strict'

urlRoot = 'views'
prefix = '#/'

items = [ 'dashboard', 'account']

appConfig = []

capitalize = (str) ->
    str[0].toUpperCase() + str[1..]

createItem = (name) ->
    name: capitalize(name)
    uri: "#/#{name}"
    URL: "/#{name}"
    templateUrl: "#{urlRoot}/#{name}.html"
    controller: "#{capitalize(name)}Controller"

angular.module('muse', ['ngRoute', 'navbar', 'ui.bootstrap', 'ui.codemirror', 'ui.router', 'muse.controllers'])

.config(['$stateProvider', '$routeProvider', '$locationProvider', ($stateProvider, $routeProvider, $locationProvider) ->
    appConfig = (createItem(item) for item in items)
    console?.log appConfig
    $stateProvider.state(it.name, it) for it in appConfig

    $routeProvider.otherwise(
        redirectTo: appConfig[0].URL
    )

    $locationProvider.html5Mode(false)
])

.run(['$rootScope', '$state', '$stateParams', ($rootScope, $state, $stateParams) ->
    $rootScope.$state = $state
    $rootScope.$stateParams = $stateParams
])

.controller('MenuController', ($scope) ->
    $scope.items = appConfig
    $scope.select = ($scope) ->
        console.log "MenuController.select"
)
