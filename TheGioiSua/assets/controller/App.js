var app = angular.module("myApp", ["ngRoute"]);
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
                templateUrl: "/assets/views/product.html",
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
                redirectTo: "/login",
            });
        $locationProvider.hashPrefix(''); // Bỏ prefix hash
    },
]);