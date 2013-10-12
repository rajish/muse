'use strict'

angular.module("muse.directives", [])

.directive "appVersion", ["version", (version) ->
  (scope, elm, attrs) ->
    elm.text version
]
