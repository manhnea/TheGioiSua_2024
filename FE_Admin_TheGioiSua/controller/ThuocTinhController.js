var app = angular.module("productApp", []);

app.controller("ProductController", function ($scope, $http) {
    $scope.brands = [];
    $scope.newBrand = {};
    $http
        .get("http://localhost:1234/api/Milkbrand/lst")
        .then(function (response) {
            $scope.brands = response.data;
        })
        .catch(function (error) {
            console.error("Lỗi khi lấy danh sách thương hiệu:", error);
        });

    $scope.addBrand = function () {
        $http
            .post("http://localhost:1234/api/Milkbrand/add", $scope.newBrand)
            .then(function (response) {
                alert("Thêm thương hiệu thành công!");
                $scope.brands.push(response.data);
                $scope.newBrand = {};
            })
            .catch(function (error) {
                console.error("Lỗi khi thêm thương hiệu:", error);
            });
    };
});