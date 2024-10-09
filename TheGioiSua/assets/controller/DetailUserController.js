var app = angular.module("myApp");

app.controller("DetailController", [
    "$scope",
    "$location",
    "$http", // Thêm $http vào controller
    function ($scope, $location, $http) {
        // Lấy thông tin người dùng từ localStorage
        const userInfo = localStorage.getItem("userInfo");
        if (userInfo) {
            $scope.user = JSON.parse(userInfo);
            $scope.account = $scope.user.user;

            // Gọi hàm API với userId
            const userId = $scope.user.id; // Giả sử bạn có userId trong user
            getUserById(userId);
        }

        // Hàm gọi API để lấy thông tin người dùng theo userId
        function getUserById(userId) {
            $http.get(`http://localhost:1234/api/user/${userId}`)
                .then(function (response) {
                    // Xử lý phản hồi từ API
                    $scope.userData = response.data; // Gán dữ liệu nhận được từ API vào scope
                    console.log($scope.userData);
                })
                .catch(function (error) {
                    // Xử lý lỗi
                    console.error('Error fetching user data:', error);
                    // Có thể thêm thông báo lỗi cho người dùng nếu cần
                });
        }
        $scope.logout = function () {
            localStorage.removeItem("token");
            localStorage.removeItem("userInfo");
            window.location.href = "/";
        };
    },
]);