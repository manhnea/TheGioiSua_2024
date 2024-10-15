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
<<<<<<< HEAD

      $http.post("http://localhost:1234/api/user/authenticate", userCredentials)
        .then(
          function (response) {
            // Kiểm tra nếu mã trạng thái là 200
            if (response.status === 200) {
              localStorage.setItem("token", response.data.token);
              const userInfo = parseJwt(response.data.token);
              localStorage.setItem("userInfo", JSON.stringify(userInfo));
              // window.location.href = "/"; // Chuyển hướng đến trang chính
            } else {
              // Xử lý các mã trạng thái khác nếu cần
              $scope.errorMessage = "Unexpected response: " + response.status;
            }
          },
          function (errorResponse) {
            // Nếu có lỗi trong quá trình đăng nhập
            if (errorResponse.data && errorResponse.data.error) {
              $scope.errorMessage = errorResponse.data.error; // Thông báo lỗi từ phản hồi
            } else {
              $scope.errorMessage = "Login failed: Invalid username or password"; // Thông báo chung nếu không có thông tin cụ thể
            }
=======
      if (!$scope.username || !$scope.password) {
        $scope.errorMessage = "Vui lòng nhập tên đăng nhập và mật khẩu.";
        return;
      }
      $http
        .post("http://localhost:1234/api/user/authenticate", userCredentials)
        .then(
          function (response) {
            // Xử lý phản hồi thành công
            localStorage.setItem("token", response.data.token);
            const userInfo = parseJwt(response.data.token);
            localStorage.setItem("userInfo", JSON.stringify(userInfo));
            $location.path('/home')
          },
          function (errorResponse) {
            $scope.errorMessage = errorResponse.data.error;
>>>>>>> f42d72b (fix login/register)
          }
        );

    };

  },
]);
