'use strict';

angular.module('tuxAdminApp')
    .controller('takeVacationDetailFailController', 
    		['$scope', '$stateParams', '$uibModalInstance', 'entity', 'takeVacationDetailService',
    		function($scope, $stateParams, $uibModalInstance, entity, takeVacationDetailService) {
    			$scope.takeVacationDetail = entity;
				var onSaveSuccess = function (result) {
				        $scope.isSaving = false;
				        $uibModalInstance.close(result);
				};
			
			    var onSaveError = function (result) {
			        $scope.isSaving = false;
			    };
			
			    $scope.save = function () {
			    	$scope.takeVacationDetail.status=0;
			     takeVacationDetailService.update($scope.takeVacationDetail, onSaveSuccess, onSaveError);
			    };
			    $scope.clear = function() {
			        $uibModalInstance.dismiss('cancel');
			};
    }]);
