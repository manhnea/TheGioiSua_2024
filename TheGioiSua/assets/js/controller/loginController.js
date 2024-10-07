// Khởi tạo ứng dụng AngularJS
var app = angular.module('myApp');

// Controller cho trang đăng nhập
app.controller('LoginController', ['$scope', '$http', 'UserService', function($scope, $http, UserService) {
    $scope.username = '';
    $scope.password = '';
    $scope.errorMessage = '';
    $scope.userInfo = null;

    // Hàm giải mã JWT
    function parseJwt(token) {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(window.atob(base64));
    }

    // Hàm đăng nhập
    $scope.login = function() {
        const userCredentials = {
            username: $scope.username,
            password: $scope.password
        };

        // Gửi yêu cầu đăng nhập đến backend
        $http.post('http://localhost:1234/api/user/authenticate', userCredentials)
            .then(function(response) {
                // Lưu token vào localStorage hoặc sessionStorage
                localStorage.setItem('token', response.data.token);

                // Lấy thông tin người dùng từ token và lưu vào UserService
                const userInfo = parseJwt(response.data.token);
                UserService.setUserInfo(userInfo); // Lưu thông tin vào UserService

                // Chuyển hướng người dùng đến trang home hoặc dashboard
                window.location.href = '/index.html'; // Điều hướng sau khi đăng nhập thành công
            }, function(error) {
                // Xử lý lỗi
                $scope.errorMessage = "Login failed: Invalid username or password";
            });
    };
}]);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: '/views/login.html',
            controller: 'LoginController'
        })
        .otherwise({
            redirectTo: '/login' // Điều hướng về login nếu không có đường dẫn nào khớp
        });
}]);