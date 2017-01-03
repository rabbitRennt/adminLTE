'use strict';

angular.module('tuxAdminApp').controller('takeVacationDetailDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'takeVacationDetailService', 'Language',
        function($scope, $stateParams, $uibModalInstance, entity, takeVacationDetailService, Language) {
    	
        $scope.takeVacationDetail = entity;
        $scope.authorities = ["ROLE_USER", "ROLE_USER"];
        Language.getAll().then(function (languages) {
            $scope.languages = languages;
        });
        var onSaveSuccess = function (result) {
            $scope.isSaving = false;
            $uibModalInstance.close(result);
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.takeVacationDetail.id != null) {
            	takeVacationDetailService.update($scope.takeVacationDetail, onSaveSuccess, onSaveError);
            } else {
            	takeVacationDetailService.save($scope.takeVacationDetail, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        
       /* $scope.load3 = function() {
            $('#datepicker3').datetimepicker({
            	autoclose: true,
            	format: 'yyyy-mm-dd hh:ii:ss'
            });
           
        };
        $scope.load4 = function() {
        	// 	alert(22);
        	 $('#datepicker4').datetimepicker({
             	autoclose: true,
             	format: 'yyyy-mm-dd hh:ii:ss'
             });
        }*/
}]);
