app.controller("LoginController", [
  "$scope",
  "$http",
  "$location",
  function ($scope, $http, $location) {
    $scope.username = "";
    $scope.password = "";
    $scope.errorMessage = "";
    $scope.userInfo = null;

    // Hàm giải mã JWT
    function parseJwt(token) {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(window.atob(base64));
    }

    // Kiểm tra JWT có hết hạn không
    function isTokenExpired(token) {
      const decodedToken = parseJwt(token);
      const currentTime = Math.floor(Date.now() / 1000);
      return decodedToken.exp < currentTime;
    }

    $scope.login = function () {
      const userCredentials = {
        username: $scope.username,
        password: $scope.password,
      };

      // Kiểm tra tên đăng nhập và mật khẩu
      if (!$scope.username || !$scope.password) {
        $scope.errorMessage = "Vui lòng nhập tên đăng nhập và mật khẩu.";
        return;
      }

      // Gửi yêu cầu đăng nhập
      $http
        .post("http://localhost:1234/api/user/authenticate", userCredentials)
        .then(
          function (response) {
            if (response.status === 200) {
              const token = response.data.token;
              if (!isTokenExpired(token)) {
                localStorage.setItem("token", token);
                const userInfo = parseJwt(token);
                localStorage.setItem("userInfo", JSON.stringify(userInfo));

                // Chuyển hướng đến trang chính
                $location.path("/home");
              } else {
                $scope.errorMessage =
                  "Phiên đăng nhập đã hết hạn. Vui lòng thử lại.";
              }
            } else {
              $scope.errorMessage = "Unexpected response: " + response.status;
            }
          },
          function (errorResponse) {
            // Xử lý lỗi đăng nhập
            if (errorResponse.status === 401) {
              $scope.errorMessage = "Tên đăng nhập hoặc mật khẩu không đúng.";
            } else if (errorResponse.data && errorResponse.data.error) {
              $scope.errorMessage = errorResponse.data.error;
            } else {
              $scope.errorMessage = "Đã xảy ra lỗi trong quá trình đăng nhập.";
            }
          }
        );
    };
  },
]);
