'use strict';

angular.module('tuxAdminApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, ENV ,User) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';
        debugger
        if ($scope.isAuthenticated() == true)
        	$scope.user = Principal.getIdentity();
        
    	console.log(Principal.getIdentity());
    	
    	$scope.load = function (login) {
            User.get({login: login}, function(result) {
                $scope.firstName = result.firstName;
                $scope.lastName = result.lastName;
                $scope.createdDate = result.createdDate;
            });
        };
        
        if ($scope.isAuthenticated() == true)
        	$scope.load($scope.login);

        $scope.logout = function () {
            Auth.logout();
            $state.go('login');
        };
        
    });
