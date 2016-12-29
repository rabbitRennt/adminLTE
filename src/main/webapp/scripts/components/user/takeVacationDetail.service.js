'use strict';

angular.module('tuxAdminApp')
    .factory('takeVacationDetailService', function ($resource) {
        return $resource('api/takeVacationDetail/:id', {}, {
                'query': {method: 'GET', isArray: true},
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'save': { method:'POST' },
                'update': { method:'PUT' },
                'delete':{ method:'DELETE'},
                'patch':{method:'PATCH'}
            });
        })
	.factory('takeVacationService', function ($resource) {
        return $resource('api/takeVacation/:id', {}, {
                'query': {method: 'GET'},
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                }
            });
        });
