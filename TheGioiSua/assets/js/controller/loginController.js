app.controller('loginController', function ($scope, $http) {
    $scope.user = {};
    $scope.errorMessage = '';

    $scope.login = function () {
        var data = {
            username: $scope.user.username,
            password: $scope.user.password
        };

        $http.post('http://localhost:1234/api/user/authenticate', data)
            .then(function (response) {
                if (response.data.token) {
                    var token = response.data.token;
                    console.log("Token received: ", token);

                    // Lưu token vào localStorage (hoặc sessionStorage)
                    localStorage.setItem('jwtToken', token);

                    // Điều hướng người dùng đến trang khác hoặc tiếp tục xử lý
                } else {
                    $scope.errorMessage = "Login failed: Invalid token received.";
                }
            })
            .catch(function (error) {
                $scope.errorMessage = "Login failed: Invalid credentials or server error.";
                console.error("Login failed: ", error);
            });
    };
});