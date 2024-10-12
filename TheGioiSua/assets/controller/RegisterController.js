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
            email: ""
        };

        $scope.successMessage = "";
        $scope.errorMessage = "";
        $scope.submitted = false;  // Biến theo dõi xem form đã được submit hay chưa

        // Hàm submit form
        $scope.submitForm = function () {
            $scope.submitted = true; // Đánh dấu form đã được submit

            // Kiểm tra tính hợp lệ của form
            if ($scope.registrationForm.$valid) {
                // Nếu form hợp lệ, gửi thông tin người dùng đến server để đăng ký
                $http.post("http://localhost:1234/api/user/register", $scope.user)
                    .then(function (response) {
                        localStorage.setItem("userInfo", JSON.stringify(response.data));
                        $scope.successMessage = "Registration successful!";
                        $location.path("/detailUser");
                        $scope.account = response.data.username;
                        $scope.$apply();
                    })
                    .catch(function (error) {
                        if (error.data && error.data.message) {
                            $scope.errorMessage = "Registration failed: " + error.data.message;
                        } else {
                            $scope.errorMessage = "Registration failed: Unknown error";
                        }
                    });
            } else {
                // Nếu form không hợp lệ
                $scope.errorMessage = "Form is invalid. Please correct the errors and try again.";
            }
        };
    }
]);
