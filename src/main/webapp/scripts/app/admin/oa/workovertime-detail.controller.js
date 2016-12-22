'use strict';

angular.module('tuxAdminApp')
    .controller('WorkOvertimeDetailController', function ($scope, $stateParams, WorkOvertimeService) {
        $scope.workOvertime = {};
        $scope.load = function (id) {
        	WorkOvertimeService.get({id: id}, function(result) {
                $scope.workOvertime = result;
            });
        };
        $scope.load($stateParams.id);
    });
