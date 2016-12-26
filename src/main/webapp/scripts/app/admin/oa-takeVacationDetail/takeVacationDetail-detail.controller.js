'use strict';

angular.module('tuxAdminApp')
    .controller('takeVacationDetailDetailController', function ($scope, $stateParams, takeVacationDetailService) {
        $scope.takeVacationDetail = {};
        $scope.load = function (id) {
        	takeVacationDetailService.get({id: id}, function(result) {
                $scope.takeVacationDetail = result;
            });
        };
        $scope.load($stateParams.id);
    });
