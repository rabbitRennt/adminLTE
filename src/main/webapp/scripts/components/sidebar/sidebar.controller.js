'use strict';

angular.module('tuxAdminApp')
    .controller('SidebarController', function ($scope, $location, $state, Account,Auth, Principal, ENV, User) {
    	$scope.isAuthenticated = Principal.isAuthenticated;
        //$scope.username = JSON.stringify(Principal);
    	
        $scope.username = Principal.getIdentity().login;
    	$scope.userImage ="/dist/img/user2-160x160.jpg";
    	
    	$scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
        
        $scope.setActive = function (id) {
        	if($("#"+id).attr('class') == "treeview active"){
        		$("#"+id).attr('class','treeview');
        	}else{
        		$('.treeview').attr('class','treeview');
        		$("#"+id).attr('class','treeview active');
        	} 
        };
        
        $scope.setMenuToActive = function (id) {
        	if($("#"+id).attr('class') == ""){
        		$("#"+id).attr('class','active');
        	}else{
        		$("#"+id).attr('class','');
        	} 
        }; 
    });
