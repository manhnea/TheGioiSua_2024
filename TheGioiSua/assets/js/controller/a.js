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