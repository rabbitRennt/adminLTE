'use strict';

angular.module('tuxAdminApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oa-workovertime', {
            	parent: 'admin',
                url: '/oa',
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
                        $translatePartialLoader.addPart('oa.workovertime');
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
	                    $translatePartialLoader.addPart('oa.workovertime');
	                    return $translate.refresh();
	                }]
	            }
	        })
           .state('oa-workovertime.new', {
                parent: 'admin',
                url: '/oa/new',
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
                    $state.go('oa-workovertime');
                })
            }]
        })
            .state('oa-workovertime.delete', {
                parent: 'oa-workovertime',
                url: '/oa/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/admin/oa/workovertime-delete-dialog.html',
                        controller: 'workOvertimeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['WorkOvertimeService', function(WorkOvertimeService) {
                                return WorkOvertimeService.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('oa-workovertime', null, { reload: true });
                    }, function() {
                        $state.go('oa-workOvertime');
                    })
                }]
            })
            .state('oa-workovertime.update', {
	            parent: 'admin',
	            url: '/oa/{id}/update',
	            data: {
	                authorities: ['ROLE_ADMIN'],
	            },
	            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
	                $uibModal.open({
	                    templateUrl: 'scripts/app/admin/oa/workovertime-fail.html',
	                    controller: 'workOvertimeFailController',
	                    size: 'lg',
	                    resolve: {
	                        entity: ['WorkOvertimeService', function(takeVacationDetailService) {
	                            return takeVacationDetailService.get({id : $stateParams.id});
	                        }]
	                    }
	                }).result.then(function(result) {
	                    $state.go('oa-workovertime', null, { reload: true });
	                }, function() {
	                    $state.go('oa-workOvertime');
	                })
	            }]
	        });
        
    });
