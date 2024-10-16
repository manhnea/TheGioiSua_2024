var app = angular.module("myApp", ["ngRoute"]);

app.config(function ($routeProvider) {
  $routeProvider
    .when("/home", {
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
      redirectTo: "/login",
    });
});
app.service("AuthService", function () {
  this.getToken = function () {
    return localStorage.getItem("token");
  };

  this.getUserInfo = function () {
    const userInfo = localStorage.getItem("userInfo");
    return userInfo ? JSON.parse(userInfo) : null;
  };

  this.clearSession = function () {
    localStorage.removeItem("token");
    localStorage.removeItem("userInfo");
  };
});
