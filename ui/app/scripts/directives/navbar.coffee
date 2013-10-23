'use strict'

angular.module('navbar', [])

.controller('NavbarController', ($scope) ->
    console?.log 'NavbarController'
)

.directive('navbar', () ->
    controller: 'NavbarController'
    templateUrl: 'ui/templates/navbar/navbar.html'
)
