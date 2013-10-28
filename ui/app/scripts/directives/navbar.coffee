'use strict'

angular.module('navbar',[])

.controller('NavbarController', ['$scope', ($scope) ->
    console?.log "NavbarController function"
    ctrl = @
    @items = []

    @select = (item) ->
        console?.log "Navbar->select(#{item})"
        angular.forEach(ctrl.items, (item) ->
            item.active = false
        )
        item.active = true

    @addItem = (item) ->
        console?.log "Navbar->addItem(#{item})"
        ctrl.items.push item
        ctrl.select(item) if ctrl.items.length == 1 || item.active

    @removeItem = (item) ->
        console?.log "Navbar->removeItem(#{item})"
        index = ctrl.items.indexOf(item)
        if item.active && ctrl.items.length > 1
            newActiveItem = (if index is tabs.length - 1 then index - 1 else index + 1)
            ctrl.select(ctrl.items[newActiveItem])
        ctrl.items.splice(index, 1)
])

.directive('navbar', () ->
    restrict: 'EA'
    templateUrl: 'templates/navbar/navbar.html'
    controller: 'NavbarController'
    transclude: true
    replace: true
    require: '^navbar'
    scope: {}
    compile: (el, atr, transclude) ->
        (scope, element, attrs, navbarCtrl) ->
            scope.collapse = (if angular.isDefined(attrs.ngCollapse) then true else false)
            scope.brand = attrs.brand
            scope.brandHref = attrs.brandHref
            navbarCtrl.$scope = scope
            navbarCtrl.$transcludeFn = transclude
            navbarCtrl
)

.directive('navItem',['$parse', ($parse) ->
    require: '^navbar'
    restrict: 'EA'
    replace: true
    templateUrl: 'templates/navbar/navitem.html'
    transclude: true
    scope:
        target: '@'
        active: '@'
        disabled: '@'
        onSelect: '&select'
        onDeselect: '&deselect'
    controller: () -> {}
    compile: (element, attrs, transclude) ->
        (scope, element, attrs, navbarCtrl) ->
            scope.$watch 'active', (active) ->
                if active
                    navbarCtrl.select(scope)
                    scope.onSelect()
                else
                    scope.onDeselect()
            scope.select = () -> scope.active = not scope.disabled
            navbarCtrl.addItem(scope)
            scope.$on 'destroy', () -> navbarCtrl.removeItem(scope)
            navbarCtrl.$scope = scope
            navbarCtrl.$transcludeFn = transclude
            navbarCtrl
])
