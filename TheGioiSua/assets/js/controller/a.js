var token = localStorage.getItem('jwtToken'); // Hoặc sessionStorage.getItem('jwtToken');
if (token) {
    console.log("Token retrieved: ", token);
    // Gửi token trong header Authorization cho các yêu cầu API khác
    $http({
        method: 'GET',
        url: 'http://localhost:1234/api/protected-resource',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    }).then(function (response) {
        console.log("Protected data: ", response.data);
    }).catch(function (error) {
        console.error("Error accessing protected resource: ", error);
    });
} else {
    console.error("No token found. User might not be logged in.");
}
// assets/js/controller/MainController.js
angular.module('myApp')
    .controller('MainController', ['$scope', '$location', function ($scope, $location) {
        $scope.isLoginPage = false;

        // Theo dõi sự thay đổi URL
        $scope.$on('$routeChangeSuccess', function () {
            // Kiểm tra xem đường dẫn hiện tại có phải là trang đăng nhập không
            if ($location.path() === '/login') {
                $scope.isLoginPage = true; // Ẩn header và footer
            } else {
                $scope.isLoginPage = false; // Hiển thị header và footer
            }
        });
    }]);
// assets/js/app.js
angular.module('myApp', ['ngRoute'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/home', {
                templateUrl: 'assets/views/home.html',
                controller: 'HomeController'
            })
            .when('/products', {
                templateUrl: 'assets/views/products.html',
                controller: 'ProductController'
            })
            .when('/contact', {
                templateUrl: 'assets/views/contact.html',
                controller: 'ContactController'
            })
            .when('/login', {
                templateUrl: 'assets/views/login.html', // Đường dẫn tới tệp HTML cho trang đăng nhập
                controller: 'LoginController'
            })
            .otherwise({
                redirectTo: '/home'
            });
    }]);
