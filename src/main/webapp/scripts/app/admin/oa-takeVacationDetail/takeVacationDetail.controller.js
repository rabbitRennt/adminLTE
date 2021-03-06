'use strict';

angular.module('tuxAdminApp')
    .controller('takeVacationDetailController', function ($scope, takeVacationDetailService,takeVacationService, ParseLinks,$uibModal) {
    	
    	$scope.takeVacationDetails = [];
    	$scope.takeVacation = {};
        $scope.authorities = ["ROLE_USER", "ROLE_USER"];

        $scope.page = 1;
        $scope.loadAll = function () {
        	takeVacationDetailService.query({page: $scope.page - 1, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.takeVacationDetails = result;
            });
        	takeVacationService.query(function (result, headers) {
                $scope.takeVacation = result;
            });
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        }
        $scope.loadAll();
        console.log($scope.takeVacationDetail);

        $scope.pass = function (param) {
        	takeVacationDetailService.patch(param, function () {
                $scope.loadAll();
                $scope.clear();
            });
        };
       

        $scope.clear = function () { 
            $scope.takeVacationDetail = {
            		id: null, createdBy: null, createdDate: null, startDate: null, endDate: null,
                    timeLength: null, status: null, remark: null
            };
     //       $scope.editForm.$setPristine();
      //      $scope.editForm.$setUntouched();
        };
    });
