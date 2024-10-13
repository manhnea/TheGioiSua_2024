var app = angular.module("myApp", ["ngRoute"]);

app.config(function ($routeProvider) {
  $routeProvider
    .when("/login", {
      templateUrl: "index.html",
      controller: "IndexController",
    })
    .when("/index", {
      templateUrl: "home.html",
      controller: "HomeController",
    })
    .when("/home", {
      templateUrl: "views/home.html",
      controller: "HomeController",
    })
    .when("/table-data-table", {
      templateUrl: "views/table-data-table.html",
      controller: "ContactController",
    })
    .when("/form-add-nhan-vien", {
      templateUrl: "views/form-add-nhan-vien.html",
      controller: "ContactController",
    })
    .when("/table-data-khach-hang", {
      templateUrl: "views/table-data-khach-hang.html",
      controller: "ContactController",
    })
    .when("/form-add-san-pham", {
      templateUrl: "views/form-add-san-pham.html",
      controller: "ContactController",
    })
    .when("/quan-ly-bao-cao", {
      templateUrl: "views/quan-ly-bao-cao.html",
      controller: "ContactController",
    })
    .when("/table-data-oder", {
      templateUrl: "views/table-data-oder.html",
      controller: "ContactController",
    })
    .when("/table-data-product", {
      templateUrl: "views/table-data-product.html",
      controller: "ContactController",
    })
    .otherwise({
      redirectTo: "/",
    });
});
app.controller("HomeController", function ($scope) {
  $scope.message = "Welcome to the Home Page!";
});

app.controller("AboutController", function ($scope) {
  $scope.message = "This is the About Page.";
});

app.controller("ContactController", function ($scope) {
  $scope.message = "Contact us at contact@example.com.";
});
