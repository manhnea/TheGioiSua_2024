var app = angular.module("myApp", ["ngRoute"]);

app.config(function ($routeProvider, $location) {
  $rootScope.$location = $location;
  $routeProvider
    .when("/", {
      templateUrl: "doc/views/home.html",
      controller: "IndexController",
    })
    .when("/table-data-table", {
      templateUrl: "doc/views/table-data-table.html",
      controller: "DataTableController",
    })
    .when("/form-add-nhan-vien", {
      templateUrl: "doc/views/form-add-nhan-vien.html",
      controller: "NhanVienController",
    })
    .when("/table-data-khach-hang", {
      templateUrl: "doc/views/table-data-khach-hang.html",
      controller: "KhachHangController",
    })
    .when("/form-add-san-pham", {
      templateUrl: "doc/views/form-add-san-pham.html",
      controller: "SanPhamController",
    })
    .when("/quan-ly-bao-cao", {
      templateUrl: "doc/views/quan-ly-bao-cao.html",
      controller: "BaoCaoController",
    })
    .when("/table-data-oder", {
      templateUrl: "doc/views/table-data-oder.html",
      controller: "DataOderController",
    })
    .when("/table-data-product", {
      templateUrl: "doc/views/table-data-product.html",
      controller: "DataProductController",
    })
    .when("/login", {
      templateUrl: "doc/views/login.html",
      controller: "LoginController",
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
