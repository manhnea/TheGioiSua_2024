app.controller("RegisterController", [
    "$scope",
    "$http",
    "$location",
    function ($scope, $http, $location) {
        $scope.user = {
            username: "",
            password: "",
            fullname: "",
            email: ""
        };
        
        $scope.successMessage = "";
        $scope.errorMessage = "";

        $scope.submitForm = function () {
            // Gửi thông tin người dùng đến server để đăng ký
            $http.post("/api/register", $scope.user)
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
