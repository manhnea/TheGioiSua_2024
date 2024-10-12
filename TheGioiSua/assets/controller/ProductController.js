app.controller("ProductController", [
    "$scope",
    "$location",
    "$http",
    function ($scope, $location, $http) {
        // Khởi tạo mảng để lưu trữ sản phẩm
        $scope.products = [];
        $scope.currentPage = 0; // Trang hiện tại
        $scope.pageSize = 12; // Số sản phẩm trên mỗi trang
        $scope.totalPages = 0; // Tổng số trang

        // Hàm để lấy danh sách sản phẩm
        $scope.loadProducts = function(page) {
            // Gọi API để lấy sản phẩm
            $http.get(`http://localhost:1234/api/Product/getPage?page=${page}&size=${$scope.pageSize}`)
                .then(function(response) {
                    $scope.products = response.data.content; // Lưu trữ dữ liệu sản phẩm vào biến
                    $scope.totalPages = response.data.totalPages; // Cập nhật tổng số trang
                    console.log($scope.products);
                })
                .catch(function(error) {
                    console.error("Error fetching products:", error);
                });
        };

        // Hàm chuyển trang
        $scope.changePage = function(page) {
            if (page < 0 || page > $scope.totalPages) return; // Kiểm tra phạm vi trang
            $scope.currentPage = page; // Cập nhật trang hiện tại
            $scope.loadProducts(page); // Gọi hàm tải sản phẩm với trang mới
        };

        // Gọi hàm để lấy dữ liệu sản phẩm khi controller được khởi tạo
        $scope.loadProducts($scope.currentPage);
    },
]);
