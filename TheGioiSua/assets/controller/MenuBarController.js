app.controller("MenuBarController", [
    "$scope",
    "$location",
    "$http", // Thêm $http vào controller
    function ($scope, $location, $http) {
        // Khởi tạo các biến để lưu trữ dữ liệu
        $scope.milkTypes = [];
        $scope.milkBrands = [];
        $scope.targetUsers = [];
        
        // Biến để lưu trữ lựa chọn của người dùng
        $scope.selectedMilkType = null;
        $scope.selectedPriceRange = null;
        $scope.selectedMilkBrand = null;
        $scope.selectedTargetUser = null;

        // Hàm để lấy danh sách loại sữa
        $scope.loadMilkTypes = function() {
            $http.get("http://localhost:1234/api/Milktype/lst")
                .then(function(response) {
                    $scope.milkTypes = response.data; // Lưu trữ dữ liệu vào biến
                })
                .catch(function(error) {
                    console.error("Error fetching milk types:", error);
                });
        };

        // Hàm để lấy danh sách thương hiệu sữa
        $scope.loadMilkBrands = function() {
            $http.get("http://localhost:1234/api/Milkbrand/lst")
                .then(function(response) {
                    $scope.milkBrands = response.data; // Lưu trữ dữ liệu vào biến
                })
                .catch(function(error) {
                    console.error("Error fetching milk brands:", error);
                });
        };

        // Hàm để lấy danh sách người dùng mục tiêu
        $scope.loadTargetUsers = function() {
            $http.get("http://localhost:1234/api/Targetuser/lst")
                .then(function(response) {
                    $scope.targetUsers = response.data; // Lưu trữ dữ liệu vào biến
                })
                .catch(function(error) {
                    console.error("Error fetching target users:", error);
                });
        };

        // Hàm tìm kiếm
        $scope.search = function() {
            console.log("Selected Milk Type:", $scope.selectedMilkType);
            console.log("Selected Price Range:", $scope.selectedPriceRange);
            console.log("Selected Milk Brand:", $scope.selectedMilkBrand);
            console.log("Selected Target User:", $scope.selectedTargetUser);
            // Thực hiện tìm kiếm dựa trên các tiêu chí đã chọn
            console.log('a')
        };

        // Gọi các hàm để lấy dữ liệu khi controller được khởi tạo
        $scope.loadMilkTypes();
        $scope.loadMilkBrands();
        $scope.loadTargetUsers();
    },
]);
