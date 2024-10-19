var _0x1f55 = [
  "value",
  "username",
  "getElementById",
  "password-field",
  "admin",
  "123456",
  "",
  "Xin chào Võ Trường",
  "success",
  "location",
  "doc/index.html#!/home",
  "Bạn chưa điền đầy đủ thông tin đăng nhập...",
  "error",
  "Thử lại",
  "Bạn chưa nhập mật khẩu...",
  "warning",
  "Tài khoản đang để trống...",
  "Mật khẩu đang để trống...",
  "Sai thông tin đăng nhập hãy kiểm tra lại...",
  "test",
  "Bạn vui lòng nhập đúng định dạng email...",
  "focus",
  "Chúng tôi vừa gửi cho bạn email hướng dẫn đặt lại mật khẩu vào địa chỉ cho bạn",
  "Đóng",
  "#",
];
function validate() {
  var _0x9903x2 = document[_0x1f55[2]](_0x1f55[1])[_0x1f55[0]];
  var _0x9903x3 = document[_0x1f55[2]](_0x1f55[3])[_0x1f55[0]];
  if (_0x9903x2 == _0x1f55[4] && _0x9903x3 == _0x1f55[5]) {
    swal({
      title: _0x1f55[6],
      text: _0x1f55[7],
      icon: _0x1f55[8],
      close: true,
      button: false,
    });
    window[_0x1f55[9]] = _0x1f55[10];
    return true;
  }
  if (_0x9903x2 == _0x1f55[6] && _0x9903x3 == _0x1f55[6]) {
    swal({
      title: _0x1f55[6],
      text: _0x1f55[11],
      icon: _0x1f55[12],
      close: true,
      button: _0x1f55[13],
    });
    return false;
  }
  if (_0x9903x2 == _0x1f55[4] && _0x9903x3 == _0x1f55[6]) {
    swal({
      title: _0x1f55[6],
      text: _0x1f55[14],
      icon: _0x1f55[15],
      close: true,
      button: _0x1f55[13],
    });
    return false;
  }
  if (_0x9903x2 == null || _0x9903x2 == _0x1f55[6]) {
    swal({
      title: _0x1f55[6],
      text: _0x1f55[16],
      icon: _0x1f55[15],
      close: true,
      button: _0x1f55[13],
    });
    return false;
  }
  if (_0x9903x3 == null || _0x9903x3 == _0x1f55[6]) {
    swal({
      title: _0x1f55[6],
      text: _0x1f55[17],
      icon: _0x1f55[15],
      close: true,
      button: _0x1f55[13],
    });
    return false;
  } else {
    swal({
      title: _0x1f55[6],
      text: _0x1f55[18],
      icon: _0x1f55[12],
      close: true,
      button: _0x1f55[13],
    });
    return true;
  }
}
function RegexEmail(_0x9903x5) {
  var _0x9903x6 = document[_0x1f55[2]](_0x9903x5)[_0x1f55[0]];
  var _0x9903x8 = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/[
    _0x1f55[19]
  ](_0x9903x6);
  if (!_0x9903x8) {
    swal({
      title: _0x1f55[6],
      text: _0x1f55[20],
      icon: _0x1f55[12],
      close: true,
      button: _0x1f55[13],
    });
    _0x9903x5[_0x1f55[21]];
  } else {
    swal({
      title: _0x1f55[6],
      text: _0x1f55[22],
      icon: _0x1f55[8],
      close: true,
      button: _0x1f55[23],
    });
    _0x9903x5[_0x1f55[21]];
    window[_0x1f55[9]] = _0x1f55[24];
  }
}
