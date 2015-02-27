angular
    .module('app', ['ngResource'])
    .service('UserService', function ($log, $resource) {
        return {
            getAll: function () {
                var userResource = $resource('users', {}, {
                    query: {method: 'GET', params: {}, isArray: true}
                },{
                    update: {
                        method: "PUT"
                    },
                    remove: {
                        method: "DELETE"
                    }
                });
                return userResource.query();
            }
        }
    })
    .controller('UserController', function ($scope, $log, UserService) {
        $scope.users = UserService.getAll();
    });
