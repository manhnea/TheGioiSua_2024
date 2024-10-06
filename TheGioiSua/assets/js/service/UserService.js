// userService.js
var app = angular.module('myApp', ['ngRoute']);

// Định nghĩa UserService
app.service('UserService', function() {
    var userInfo = null;

    this.setUserInfo = function(info) {
        userInfo = info;
    };

    this.getUserInfo = function() {
        return userInfo;
    };
});
