app.controller("SanPhamController", [
  "$scope",
  "$location", // Inject $location
  function ($scope, $location) {
    $scope.formData = {};

    // Function to get the token from localStorage
    function getToken() {
      return localStorage.getItem("token");
    }

    // Get the token and store it in a variable
    const token =
      "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjUiLCJzdWIiOiJtYW5obmUiLCJyb2xlIjoiQWRtaW4iLCJpYXQiOjE3MjkwODg5MTUsImV4cCI6MTcyOTEyNDkxNX0.hRalkCvfzjPn_0EbFOH34RrOMALe3gIoHlkwu2p7ZQA";

    // Configure Axios defaults
    axios.defaults.headers.common["Authorization"] = "Bearer " + token;

    function fetchBrands() {
      axios
        .get("http://localhost:1234/api/Milkbrand/lst")
        .then(function (response) {
          console.log("Fetched brand list:", response.data);
          $scope.brands = response.data.filter((brand) => brand.status === 1);
          $scope.$apply(); // Update the scope
        })
        .catch(function (error) {
          console.error("Error fetching brand list:", error);
          if (error.response && error.response.status === 401) {
            $location.path("/login");
          }
        });
    }

    fetchBrands();

    $scope.save = function () {
      const isUpdating = !!$scope.formData.id; // Check for updating
      const apiUrl = isUpdating
        ? `http://localhost:1234/api/Milkbrand/update/${$scope.formData.id}`
        : "http://localhost:1234/api/Milkbrand/add";
      const userCredentials = {
        milkbrandname: $scope.formData.milkbrandname,
        description: $scope.formData.description,
      };

      const requestMethod = isUpdating ? axios.put : axios.post;

      requestMethod(apiUrl, userCredentials)
        .then(function (response) {
          console.log(
            isUpdating
              ? "Updated brand successfully:"
              : "Added brand successfully:",
            response.data
          );
          fetchBrands(); // Refresh brand list
          $scope.formData = {}; // Clear form data
        })
        .catch(function (error) {
          console.error(
            isUpdating
              ? "Error updating milk brand:"
              : "Error adding milk brand:",
            error
          );
          if (error.response && error.response.status === 401) {
            $location.path("/login");
          }
        });
    };

    $scope.deletebrand = function (id) {
      if (confirm("Are you sure you want to delete this brand?")) {
        axios
          .delete(`http://localhost:1234/api/Milkbrand/delete/${id}`)
          .then(function (response) {
            if (response.data && response.data.message) {
              console.log(response.data.message);
            } else {
              console.error("No message returned from the server.");
            }
            fetchBrands(); // Refresh brand list
          })
          .catch(function (error) {
            console.error("Error during deletion:", error);
            if (error.response && error.response.status === 401) {
              $location.path("/login");
            }
          });
      }
    };

    $scope.fillData = function (id) {
      axios
        .get(`http://localhost:1234/api/Milkbrand/lst/${id}`)
        .then(function (response) {
          console.log("Fetched brand details:", response.data);
          $scope.formData = response.data; // Fill form data with brand details
          $scope.$apply(); // Update the scope
        })
        .catch(function (error) {
          console.error("Error fetching brand details:", error);
          if (error.response && error.response.status === 401) {
            $location.path("/login");
          }
        });
    };
  },
]);
