app.controller("SanPhamController", [
  "$scope",
  "$http",
  function ($scope, $http) {
    $http
      .get("http://localhost:1234/api/Milkbrand/lst")
      .then(function (response) {
        console.log("Fetched brand list:", response.data);
        $scope.brands = response.data;
      })
      .catch(function (error) {
        console.error("Error fetching brand list:", error);
      });
  },
]);
