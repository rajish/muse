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

.config(['$routeProvider', '$locationProvider', ($routeProvider, $locationProvider) ->
    appConfig = (createItem(item) for item in items)
    console?.log appConfig
    $routeProvider.when(it.URL, it) for it in appConfig

    $routeProvider
    .when(
        '/',
        templateUrl: "#{urlRoot}/welcome.html"
    )
    .otherwise(
        redirectTo: '/'
    )

    $locationProvider.html5Mode(false)
])

.controller('MenuController', ($scope) ->
    $scope.items = appConfig
    $scope.select = ($scope) ->
        console.log "MenuController.select"
)
