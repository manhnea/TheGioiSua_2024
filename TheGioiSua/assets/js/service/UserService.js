var app = angular.module("myApp", []);
// var app = angular.module("myApp", ["ngRoute"]);

// Định nghĩa UserService dưới dạng class
class UserService {
  constructor() {
    this.userInfo = null;
  }

  setUserInfo(info) {
    this.userInfo = info;
  }

  getUserInfo() {
    return this.userInfo;
  }
}

// Đăng ký UserService với AngularJS
app.service("UserService", UserService);
