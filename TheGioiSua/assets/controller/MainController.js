// Khởi tạo ứng dụng AngularJS với ngRoute
var app = angular.module("myApp", ["ngRoute"]);

app.controller("MainController", [
  "$scope",
  "$location",
  function ($scope, $location) {
    $scope.user = null;
    $scope.account = "Đăng Nhập";
    const userInfo = localStorage.getItem("userInfo");
    if (userInfo) {
      $scope.user = JSON.parse(userInfo);
      $scope.account = $scope.user.user;
    }
    $scope.$on('$locationChangeSuccess', function() {
      $scope.isLoginPage = $location.path() === '/login';
      $scope.isDetailPage = $location.path() === '/detailUser';
  });
  },
]);

app.config([
  "$routeProvider",
  "$locationProvider",
  function ($routeProvider, $locationProvider) {
    $routeProvider
      .when("/login", {
        templateUrl: "/assets/views/login.html",
        controller: "LoginController",
      })
      .when("/home", {
        templateUrl: "/assets/views/home.html",
        controller: "MainController",
      })
      .when("/detailUser", {
        templateUrl: "/assets/views/userDetail.html",
        controller: "DetailController",
      })
      .when("/register", {
        templateUrl: "/assets/views/register.html",
        controller: "RegisterController",
      })
      .otherwise({
        redirectTo: "/home",
      });
    // $locationProvider.html5Mode({
    //   enabled: true,
    //   requireBase: false,
    // });
    $locationProvider.html5Mode(false);
  },
]);
