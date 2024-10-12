var app = angular.module("myApp");

app.controller("RegisterController", [
  "$scope",
  "$http",
  "$location",
  function ($scope, $http, $location) {
    $scope.user = {
      username: "",
      password: "",
      fullname: "",
      email: "",
    };

    $scope.successMessage = "";
    $scope.errorMessage = "";
    $scope.submitted = false; // Biến theo dõi xem form đã được submit hay chưa

    $scope.submitForm = function () {
      $scope.submitted = true; // Đánh dấu form đã được submit

      // Kiểm tra tính hợp lệ của form
      if ($scope.registrationForm.$valid) {
        // Nếu form hợp lệ, gửi thông tin người dùng đến server để đăng ký
        $http
          .post("http://localhost:1234/api/user/register", $scope.user)
          .then(function (response) {
            // Lưu thông tin người dùng vào localStorage
            localStorage.setItem("userInfo", JSON.stringify(response.data));
            $scope.successMessage = "Registration successful!"; // Hiển thị thông báo thành công
            $location.path("/detailUser"); // Chuyển hướng đến trang thông tin người dùng sau khi đăng ký thành công
            $scope.account = response.data.username; // Cập nhật tài khoản trong MainController // Cập nhật giá trị tài khoản
            $scope.$apply(); // Cập nhật scope
          })
          .catch(function (error) {
            // Kiểm tra nếu error.data tồn tại trước khi truy cập thuộc tính message
            if (error.data && error.data.message) {
              $scope.errorMessage =
                "Registration failed: " + error.data.message;
            } else {
              $scope.errorMessage = "Registration failed: Unknown error";
            }
          });
      } else {
        // Nếu form không hợp lệ
        $scope.errorMessage =
          "Form is invalid. Please correct the errors and try again.";
      }
    };
  },
]);
app.controller("RegisterController", [
  "$scope",
  "$http",
  "$location",
  function ($scope, $http, $location) {
    $scope.user = {
      username: "",
      password: "",
      fullname: "",
      email: "",
    };

    $scope.successMessage = "";
    $scope.errorMessage = "";

    $scope.submitForm = function () {
      // Gửi thông tin người dùng đến server để đăng ký
      $http
        .post("/api/register", $scope.user)
        .then(function (response) {
          // Lưu thông tin người dùng vào localStorage
          localStorage.setItem("userInfo", JSON.stringify(response.data));

          // Hiển thị thông báo thành công
          $scope.successMessage = "Registration successful!";

          // Chuyển hướng đến trang thông tin người dùng
          $location.path("/detailUser");
          // Cập nhật tài khoản trong MainController
          $scope.account = response.data.username; // Cập nhật giá trị tài khoản
          $scope.$apply(); // Cập nhật scope
        })
        .catch(function (error) {
          $scope.errorMessage = "Registration failed: " + error.data.message;
        });
    };
  },
]);
