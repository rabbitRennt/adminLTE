'use strict';

angular.module('tuxAdminApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oa-workovertime', {
            	parent: 'admin',
                url: '/configuration',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'oa-workovertime.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/oa/workovertime.html',
                        controller: 'WorkOvertimeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('configuration');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oa-workovertime-detail', {
            	parent: 'admin',
                url: '/workOvertime/:id',
	            data: {
	                authorities: ['ROLE_USER'],
	                pageTitle: 'oa-workovertime-detail.title'
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/app/admin/oa/workovertime-detail.html',
	                    controller: 'WorkOvertimeDetailController'
	                }
	            },
	            resolve: {
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                    $translatePartialLoader.addPart('configuration');
	                    return $translate.refresh();
	                }]
	            }
	        })
           .state('oa-workovertime.new', {
                parent: 'admin',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/admin/oa/workovertime-dialog.html',
                        controller: 'WorkovertimeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null, createdBy: null, createdDate: null, startDate: null, endDate: null,
                                    timeLength: null, status: null, remark: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('oa-workovertime', null, { reload: true });
                    }, function() {
                        $state.go('oa-workovertime');
                    })
                }]
            })
        .state('oa-workovertime.edit', {
            parent: 'admin',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'scripts/app/admin/oa/workovertime-dialog.html',
                    controller: 'WorkovertimeDialogController',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkOvertimeService', function(WorkOvertimeService) {
                            return WorkOvertimeService.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function(result) {
                    $state.go('oa-workovertime', null, { reload: true });
                }, function() {
                    $state.go('^');
                })
            }]
        });
         /*   .state('oa-workovertime.delete', {
                parent: 'oa',
                url: '/{login}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/admin/oa/workovertime-delete-dialog.html',
                        controller: 'WorkOvertimeController',
                        size: 'md',
                        resolve: {
                            entity: ['User', function(User) {
                                return User.get({login : $stateParams.login});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('oa-workovertime', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
        */
    });
