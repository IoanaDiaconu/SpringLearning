var app = angular.module('app.controllers', ['ngResource']);

app.controller('UserListCtrl', ['$scope', 'UsersFactory', 'UserFactory', '$location',
    function ($scope, UsersFactory, UserFactory, $location) {

        // callback for ng-click 'editUser':
        $scope.editUser = function (username) {
            $location.path('/user-detail/' + username);
        };

        // callback for ng-click 'username':
        $scope.deleteUser = function (username) {
            UserFactory.delete({ id: username});
            $scope.users = UsersFactory.query();
        };

        // callback for ng-click 'createUser':
        $scope.createNewUser= function () {
            $location.path('/user-creation');
        };

        $scope.users = UsersFactory.query();
    }]);
app.controller('UserCreationCtrl', ['$scope', 'UsersFactory', '$location',
    function ($scope, UsersFactory, $location) {

        // callback for ng-click 'createNewUser':
        $scope.createNewUser = function () {
            UsersFactory.create($scope.user);
            $location.path('/users');
        }
    }]);