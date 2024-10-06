$scope.logout = function () {
    localStorage.removeItem('jwtToken'); // Hoặc sessionStorage.removeItem('jwtToken');
    console.log("User logged out. Token removed.");
};