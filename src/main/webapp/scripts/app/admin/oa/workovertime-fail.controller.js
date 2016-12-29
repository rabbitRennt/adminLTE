'use strict';

angular.module('tuxAdminApp')
    .controller('workOvertimeFailController', 
    		['$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkOvertimeService',
    		function($scope, $stateParams, $uibModalInstance, entity, WorkOvertimeService) {
    			$scope.workOvertime = entity;
				var onSaveSuccess = function (result) {
				        $scope.isSaving = false;
				        $uibModalInstance.close(result);
				};
			
			    var onSaveError = function (result) {
			        $scope.isSaving = false;
			    };
			
			    $scope.save = function () {
			    	$scope.workOvertime.status=2;
			    	WorkOvertimeService.patch($scope.workOvertime, onSaveSuccess, onSaveError);
			    };
			    $scope.clear = function() {
			        $uibModalInstance.dismiss('cancel');
			};
    }]);
