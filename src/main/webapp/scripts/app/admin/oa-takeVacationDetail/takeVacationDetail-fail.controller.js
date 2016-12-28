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
<<<<<<< HEAD
			    	$scope.takeVacationDetail.status=2;
			     takeVacationDetailService.update($scope.takeVacationDetail, onSaveSuccess, onSaveError);
=======
			    	$scope.takeVacationDetail.status=0;
			     takeVacationDetailService.patch($scope.takeVacationDetail, onSaveSuccess, onSaveError);
>>>>>>> branch 'master' of https://github.com/rabbitRennt/adminLTE.git
			    };
			    $scope.clear = function() {
			        $uibModalInstance.dismiss('cancel');
			};
    }]);
