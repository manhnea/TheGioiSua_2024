app.controller("LoginController", [
    "$scope",
    "$http",
    "$location",
    function ($scope, $http, $location) {
        $scope.username = "";
        $scope.password = "";
        $scope.errorMessage = "";
        $scope.userInfo = null;

        // Function to parse JWT token
        function parseJwt(token) {
            const base64Url = token.split(".")[1];
            const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
            return JSON.parse(window.atob(base64));
        }

        // Login function
        $scope.login = function () {
            // Check if username and password are filled
            if (!$scope.username || !$scope.password) {
                $scope.errorMessage = "Vui lòng nhập tên đăng nhập và mật khẩu.";
                return;
            }

            const userCredentials = {
                username: $scope.username,
                password: $scope.password,
            };

            // Send login request
            $http.post("http://localhost:1234/api/user/authenticate", userCredentials)
                .then(
                    function (response) {
                        // Check if response is successful
                        if (response.status === 200) {
                            localStorage.setItem("token", response.data.token);
                            const userInfo = parseJwt(response.data.token);
                            localStorage.setItem("userInfo", JSON.stringify(userInfo));

                            // Redirect to home page after successful login
                            $location.path("/home");
                        } else {
                            // Handle unexpected status codes
                            $scope.errorMessage = "Unexpected response: " + response.status;
                        }
                    },
                    function (errorResponse) {
                        // Handle login errors
                        if (errorResponse.data && errorResponse.data.error) {
                            $scope.errorMessage = errorResponse.data.error;
                        } else {
                            $scope.errorMessage = "Login failed: Invalid username or password.";
                        }
                    }
                );
        };
    }
]);