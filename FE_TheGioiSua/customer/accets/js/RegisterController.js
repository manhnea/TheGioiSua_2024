app.controller("RegisterController", [
  "$scope",
  "$http",
  "$location",
  function ($scope, $http, $location) {
    // Đối tượng người dùng trong scope
    $scope.user = {
      username: "",
      fullname: "",
      email: "",
      password: "",
    };

    $scope.confirmpassword = "";
    $scope.successMessage = "";
    $scope.errorMessage = "";

    $scope.register = function () {
      // Kiểm tra xem tất cả các trường đã được điền đầy đủ chưa
      if (!$scope.user.username || !$scope.user.fullname || !$scope.user.email || !$scope.user.password) {
        $scope.errorMessage = "Vui lòng điền đầy đủ thông tin!";
        return; // Kết thúc hàm nếu có trường trống
      }

      // Kiểm tra mật khẩu và mật khẩu xác nhận
      if ($scope.user.password !== $scope.confirmpassword) {
        $scope.errorMessage = "Mật Khẩu Xác Nhận Không Khớp!";
        return; // Kết thúc hàm nếu mật khẩu không khớp
      }

      // Gửi yêu cầu đăng ký
      $http.post("http://localhost:1234/api/user/register", $scope.user)
        .then(function (response) {
          // Kiểm tra mã trạng thái
          if (response.status === 200) {
            $scope.successMessage = response.data.message; // Giả định bạn trả về { "message": "Tạo Tài Khoản Thành Công" }
            $location.path('/login'); // Chuyển hướng đến trang /login
          } else {
            // Nếu mã trạng thái không phải là 200, hiển thị thông báo lỗi
            $scope.errorMessage = response.data.error || "Đã xảy ra lỗi không xác định!";
          }
        }, function (errorResponse) {
          // Kiểm tra thông báo lỗi trong phản hồi
          if (errorResponse.data && errorResponse.data.error) {
            $scope.errorMessage = errorResponse.data.error; // Lấy thông điệp lỗi từ phản hồi
          } else {
            $scope.errorMessage = "Đã xảy ra lỗi không xác định!"; // Thông báo chung nếu không có thông tin
          }
        });

    };
  },
]);
