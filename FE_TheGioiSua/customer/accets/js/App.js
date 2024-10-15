// app.js
var app = angular.module("myApp", ["ngRoute"]);

app.config([
  "$routeProvider",
  "$locationProvider",
  function ($routeProvider, $locationProvider) {
    $routeProvider
      .when("/login", {
        templateUrl: "/customer/views/login.html",
        controller: "LoginController",
      })
      .when("/register", {
        templateUrl: "/customer/views/register.html",
        controller: "RegisterController",
      })
      .otherwise({
        redirectTo: "/login", // Redirect đến trang login mặc định
      });
      // $locationProvider.html5Mode({
      //   enabled: true,
      //   requireBase: true // Đảm bảo có thẻ <base> trong HTML
      // });
      $locationProvider.hashPrefix(''); // Bỏ prefix hash
  },
  
]);
