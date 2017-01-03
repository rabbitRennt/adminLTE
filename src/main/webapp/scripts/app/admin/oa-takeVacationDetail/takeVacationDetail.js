'use strict';

angular.module('tuxAdminApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oa-takeVacationDetail', {
            	parent: 'admin',
                url: '/oa-takeVacationDetail',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'oa-takeVacationDetail.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/oa-takeVacationDetail/takeVacationDetail.html',
                        controller: 'takeVacationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oa.takeVacationDetail');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oa-takeVacationDetail-detail', {
            	parent: 'admin',
                url: '/takeVacationDetail/:id',
	            data: {
	                authorities: ['ROLE_USER'],
	                pageTitle: 'oa-takeVacationDetail-detail.title'
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/app/admin/oa-takeVacationDetail/takeVacationDetail-detail.html',
	                    controller: 'takeVacationDetailDetailController'
	                }
	            },
	            resolve: {
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                    $translatePartialLoader.addPart('oa.takeVacationDetail');
	                    return $translate.refresh();
	                }]
	            }
	        })
	        
           .state('oa-takeVacationDetail.new', {
                parent: 'admin',
                url: '/oa-takeVacationDetail/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/admin/oa-takeVacationDetail/takeVacationDetail-dialog.html',
                        controller: 'takeVacationDetailDialogController',
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
                        $state.go('oa-takeVacationDetail', null, { reload: true });
                    }, function() {
                        $state.go('oa-takeVacationDetail');
                    })
                }]
            })
        .state('oa-takeVacationDetail.edit', {
            parent: 'admin',
            url: '/oa-takeVacationDetail/{id}/edit',
            data: {
                authorities: ['ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'scripts/app/admin/oa-takeVacationDetail/takeVacationDetail-dialog.html',
                    controller: 'takeVacationDetailDialogController',
                    size: 'lg',
                    resolve: {
                        entity: ['takeVacationDetailService', function(takeVacationDetailService) {
                            return takeVacationDetailService.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function(result) {
                    $state.go('oa-takeVacationDetail', null, { reload: true });
                }, function() {
                    $state.go('oa-takeVacationDetail');
                })
            }]
        })
            .state('oa-takeVacationDetail.delete', {
                parent: 'oa-takeVacationDetail',
                url: '/oa-takeVacationDetail/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/admin/oa-takeVacationDetail/takeVacationDetail-delete-dialog.html',
                        controller: 'takeVacationDetailDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['takeVacationDetailService', function(takeVacationDetailService) {
                                return takeVacationDetailService.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('oa-takeVacationDetail', null, { reload: true });
                    }, function() {
                        $state.go('oa-takeVacationDetail');
                    })
                }]
            })
	        .state('oa-takeVacationDetail.noPass', {
	            parent: 'admin',
	            url: '/oa-takeVacationDetail/{id}/update',
	            data: {
	                authorities: ['ROLE_ADMIN'],
	            },
	            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
	                $uibModal.open({
	                    templateUrl: 'scripts/app/admin/oa-takeVacationDetail/takeVacationDetail-fail.html',
	                    controller: 'takeVacationDetailFailController',
	                    size: 'lg',
	                    resolve: {
	                        entity: ['takeVacationDetailService', function(takeVacationDetailService) {
	                            return takeVacationDetailService.get({id : $stateParams.id});
	                        }]
	                    }
	                }).result.then(function(result) {
	                    $state.go('oa-takeVacationDetail', null, { reload: true });
	                }, function() {
	                    $state.go('oa-takeVacationDetail');
	                })
	            }]
	        });
        
        
    });
