'use strict';

angular.module('tuxAdminApp')
    .controller('WorkOvertimeController', function ($scope, WorkOvertimeService, ParseLinks) {
    	
    	$scope.workovertimes = [];
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN"];

        $scope.page = 1;
        $scope.loadAll = function () {
        	WorkOvertimeService.query({page: $scope.page - 1, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.workovertimes = result;
            });
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
        console.log($scope.workovertimes);
        
        //审核通过
        $scope.pass = function (param) {
        	WorkOvertimeService.patch(param, function () {
                $scope.loadAll();
                $scope.clear();
            });
        };

        $scope.clear = function () {
            $scope.workOvertime = {
            		id: null, createdBy: null, createdDate: null, startDate: null, endDate: null,
                    timeLength: null, status: null, remark: null
            };
//            $scope.editForm.$setPristine();
//            $scope.editForm.$setUntouched();
        };
    });
