'use strict';

angular.module('tuxAdminApp')
    .controller('SidebarController', function ($scope, $location, $state, Auth, Principal, ENV) {
        debugger;
    	$scope.isAuthenticated = Principal.isAuthenticated;
        //$scope.username = JSON.stringify(Principal);
    	$scope.username = "fan";
    	$scope.userImage ="/dist/img/user2-160x160.jpg";
    	$scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    });
