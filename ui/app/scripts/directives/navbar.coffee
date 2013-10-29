'use strict'

angular.module('navbar',[])

.controller('NavbarController', ['$scope', ($scope) ->
    console?.log "NavbarController function"
    ctrl = @
    @items = []

    @select = (item) ->
        console?.log "Navbar->select(#{item.target}, active=#{item.active})"
        angular.forEach(ctrl.items, (it) ->
            it.active = false if it.target != item.target
        )
        item.active = true

    @addItem = (item) ->
        console?.log "Navbar->addItem(#{item.target}, active=#{item.active})"
        ctrl.items.push item
        ctrl.select(item) if ctrl.items.length == 1 || item.active

    @removeItem = (item) ->
        console?.log "Navbar->removeItem(#{item.target}, active=#{item.active})"
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

.directive('navItem',['$parse', '$location', ($parse, $location) ->
    require: '^navbar'
    restrict: 'EA'
    replace: true
    templateUrl: 'templates/navbar/navitem.html'
    transclude: true
    scope:
        target: '@'
        # active: '@'
        disabled: '@'
        onSelect: '&select'
        onDeselect: '&deselect'
    controller: () -> {}
    compile: (element, attrs, transclude) ->
        (scope, element, attrs, navbarCtrl) ->
            scope.$watch 'active', (nval, oval, sc) ->
                if nval
                    console.log "scope.$watch active for #{sc.target}"
                    navbarCtrl.select(sc)
                    sc.onSelect()
                else
                    console.log "scope.$watch inactive for #{sc.target}"
                    sc.onDeselect()
            scope.active = ('#' + $location.path()).search(scope.target) > -1
            scope.disabled = false
            scope.select = () -> scope.active = not scope.disabled
            navbarCtrl.addItem(scope)
            scope.$on 'destroy', () -> navbarCtrl.removeItem(scope)
            navbarCtrl.$scope = scope
            navbarCtrl.$transcludeFn = transclude
            navbarCtrl
])
