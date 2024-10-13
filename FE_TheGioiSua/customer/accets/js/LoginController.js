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
                        console.log(userInfo);
                    },
                    function (errorResponse) {
                        $scope.errorMessage = errorResponse.data.error;
                    }
                );
        };
        
    },
]);
