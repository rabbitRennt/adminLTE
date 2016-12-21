'use strict';

angular.module('tuxAdminApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oaworkovertime', {
            	parent: 'admin',
                url: '/configuration',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'oaworkovertime.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/oa/workovertime.html',
                        controller: 'WorkOvertimeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('configuration');
                        return $translate.refresh();
                    }]
                }
            });
    });
