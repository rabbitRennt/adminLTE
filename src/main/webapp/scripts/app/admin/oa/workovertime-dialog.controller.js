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
          //Initialize Select2 Elements
          //  $(".select2").select2();

            //Datemask dd/mm/yyyy
            //$("#datemask").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
            //Datemask2 mm/dd/yyyy
           // $("#datemask2").inputmask("mm/dd/yyyy", {"placeholder": "mm/dd/yyyy"});
            //Money Euro
           // $("[data-mask]").inputmask();

            //Date range picker
            //$('#reservation').daterangepicker();
            //Date range picker with time picker
            //$('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});
            //Date range as a button
        	//	alert(111);
            $('#datepicker').datepicker({
            	autoclose: true,
            	format: 'yyyy-mm-dd'
            });
           
        };
        $scope.load2 = function() {
        	// 	alert(22);
        	 $('#datepicker2').datepicker({
             	autoclose: true,
             	format: 'yyyy-mm-dd'
             });
        }
}]);
