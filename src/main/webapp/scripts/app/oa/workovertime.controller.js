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

        $scope.setActive = function (user, isActivated) {
            user.activated = isActivated;
            User.update(user, function () {
                $scope.loadAll();
                $scope.clear();
            });
        };

        $scope.clear = function () {
            $scope.workovertime = {
                id: null, login: null, firstName: null, lastName: null, email: null,
                activated: null, langKey: null, createdBy: null, createdDate: null,
                lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                resetKey: null, authorities: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
