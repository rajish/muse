'use strict'

angular.module("muse.filters", [])

.filter "interpolate", ["version", (version) ->
  (text) ->
    String(text).replace /\%VERSION\%/g, version
]
