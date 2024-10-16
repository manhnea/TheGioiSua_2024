app.controller("LoginController", [
  "$scope",
  "$http",
  "$location",
  function ($scope, $http, $location) {
    $scope.formData = {
      username: "",
      password: "",
    };
    function parseJwt(token) {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(window.atob(base64));
    }
    $scope.submitLogin = function () {
      const userCredentials = {
        username: $scope.formData.username,
        password: $scope.formData.password,
      };
      $http
        .post("http://localhost:1234/api/user/authenticate", userCredentials)
        .then(
          function (response) {
            if (response.status === 200) {
              console.log(response.data.token);
              localStorage.setItem("token", response.data.token);
              const userInfo = parseJwt(response.data.token);

              localStorage.setItem("userInfo", JSON.stringify(userInfo));
              $location.path("/home");
            } else {
              $scope.errorMessage = "Unexpected response: " + response.status;
            }
          },

          function (errorResponse) {
            if (errorResponse.data && errorResponse.data.error) {
              $scope.errorMessage = errorResponse.data.error;
            } else {
              $scope.errorMessage =
                "Login failed: Invalid username or password.";
            }
          }
        );
      const userInfo = localStorage.getItem("userInfo");

      if (userInfo) {
        $scope.user = JSON.parse(userInfo);
        $scope.account = $scope.user.user;
        $scope.fullName = $scope.user.fullName;

        // Gọi hàm API với userId
        const userId = $scope.user.id; // Giả sử bạn có userId trong user
        getUserById(userId);
      }

      // Hàm gọi API để lấy thông tin người dùng theo userId
      function getUserById(userId) {
        $http
          .get(`http://localhost:1234/api/user/${userId}`)
          .then(function (response) {
            // Xử lý phản hồi từ API
            $scope.userData = response.data; // Gán dữ liệu nhận được từ API vào scope
            console.log($scope.userData);
          })
          .catch(function (error) {
            // Xử lý lỗi
            console.error("Error fetching user data:", error);
          });
      }
      $scope.logout = function () {
        localStorage.removeItem("token");
        localStorage.removeItem("userInfo");
        window.location.href = "/";
      };
    };
  },
]);
