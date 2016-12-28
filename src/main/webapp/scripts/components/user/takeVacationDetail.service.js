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
<<<<<<< HEAD
                'patch':{method:'PATCH'}
=======
                'patch':{ method:'PATCH'}
>>>>>>> branch 'master' of https://github.com/rabbitRennt/adminLTE.git
            });
        });
