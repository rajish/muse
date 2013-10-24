'use strict'


angular.module('rtweb.services', ['ngResource'] )

.factory('Project', ['$resource', ($resource) ->
    $resource '/api/v1/projects/:projectId', {},
        query:
            method: 'GET'
            params:
                projectId:'all'
            isArray: true
])
