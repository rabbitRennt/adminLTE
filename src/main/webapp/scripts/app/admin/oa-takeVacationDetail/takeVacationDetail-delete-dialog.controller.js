'use strict';

angular.module('tuxAdminApp')
	.controller('takeVacationDetailDeleteController', function($scope, $uibModalInstance, entity, takeVacationDetailService) {

        $scope.takeVacationDetail = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
        	takeVacationDetailService.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
