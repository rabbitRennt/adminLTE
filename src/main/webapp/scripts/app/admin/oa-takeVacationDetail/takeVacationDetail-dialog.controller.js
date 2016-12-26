'use strict';

angular.module('tuxAdminApp').controller('takeVacationDetailDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'takeVacationDetailService', 'Language',
        function($scope, $stateParams, $uibModalInstance, entity, takeVacationDetailService, Language) {

        $scope.takeVacationDetail = entity;
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
            if ($scope.takeVacationDetail.id != null) {
            	takeVacationDetailService.update($scope.takeVacationDetail, onSaveSuccess, onSaveError);
            } else {
            	takeVacationDetailService.save($scope.takeVacationDetail, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        
        $scope.load3 = function() {
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
            $('#datepicker3').datepicker({
            	autoclose: true,
            	format: 'yyyy-mm-dd'
            });
           
        };
        $scope.load4 = function() {
        	// 	alert(22);
        	 $('#datepicker4').datepicker({
             	autoclose: true,
             	format: 'yyyy-mm-dd'
             });
        }
}]);
