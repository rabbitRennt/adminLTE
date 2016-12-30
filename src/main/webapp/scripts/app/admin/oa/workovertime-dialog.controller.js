'use strict';

angular.module('tuxAdminApp').controller('WorkovertimeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkOvertimeService', 'Language',
        function($scope, $stateParams, $uibModalInstance, entity, WorkOvertimeService, Language) {

        $scope.workOvertime = entity;
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN"];
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
            if ($scope.workOvertime.id != null) {
            	WorkOvertimeService.update($scope.workOvertime, onSaveSuccess, onSaveError);
            } else {
            	WorkOvertimeService.save($scope.workOvertime, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        
        $scope.load = function() {
            $('#datepicker').datetimepicker({
            	autoclose: true,
            	format: 'yyyy-mm-dd hh:ii:ss'
            });
            
        };
        $scope.load2 = function() {
        	// 	alert(22);
        	 $('#datepicker2').datetimepicker({
             	autoclose: true,
             	format: 'yyyy-mm-dd hh:ii:ss'
             });
        }
}]);
