'use strict';

angular.module('tuxAdminApp')
	.controller('workOvertimeDeleteController', function($scope, $uibModalInstance, entity, WorkOvertimeService) {

        $scope.workOvertime = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
        	WorkOvertimeService.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
