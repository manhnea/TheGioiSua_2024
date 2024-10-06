// mainController.js
var app = angular.module('myApp'); // Sử dụng ứng dụng đã được khởi tạo trong userService.js

// Controller cho trang chính

app.controller('MainController', ['$scope', 'UserService', function($scope, UserService) {
    
    $scope.user = UserService.getUserInfo(); // Lấy thông tin người dùng từ UserService
    if($scope.user == null){
        $scope.account = 'Đăng Nhập';
    }else{
        $scope.account = "Đã Đăng Nhập";
        console.log($scope.user);
    }
}]);