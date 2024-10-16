// Khởi tạo ứng dụng AngularJS với ngRoute

app.controller("MainController", [
  "$scope",
  "$location",
  function ($scope, $location) {
    $scope.isHeader = true;
    $scope.user = null;
    $scope.account = "Đăng Nhập";
    const userInfo = localStorage.getItem("userInfo");
    if (userInfo) {
      $scope.user = JSON.parse(userInfo);
      $scope.account = $scope.user.user;
    }
    $scope.$on('$locationChangeSuccess', function () {
      $scope.isLoginPage = $location.path() === '/login';
      $scope.isDetailPage = $location.path() === '/detailUser';
    });
  },
]);


