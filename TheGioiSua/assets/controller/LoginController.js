// Khởi tạo ứng dụng AngularJS
var app = angular.module("myApp");

app.controller("LoginController", [
  "$scope",
  "$http",
  "$location",
  function ($scope, $http, $location) {
    $scope.username = "";
    $scope.password = "";
    $scope.errorMessage = "";
    $scope.userInfo = null;

    function parseJwt(token) {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(window.atob(base64));
    }

    $scope.login = function () {
      const userCredentials = {
        username: $scope.username,
        password: $scope.password,
      };

      $http
        .post("http://localhost:1234/api/user/authenticate", userCredentials)
        .then(
          function (response) {
            sessionStorage.setItem("token", response.data.token);
            const userInfo = parseJwt(response.data.token);
            sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
            window.location.href = "/";
          },
          function () {
            $scope.errorMessage = "Login failed: Invalid username or password";
          }
        );
    };

    $scope.logout = function () {
      localStorage.removeItem("jwtToken");
      console.log("User logged out. Token removed.");
    };
  },
]);
