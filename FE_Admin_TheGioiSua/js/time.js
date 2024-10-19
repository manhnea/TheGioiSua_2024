// Thời Gian
function time() {
  const weekday = [
    "Chủ Nhật",
    "Thứ Hai",
    "Thứ Ba",
    "Thứ Tư",
    "Thứ Năm",
    "Thứ Sáu",
    "Thứ Bảy",
  ];
  setInterval(() => {
    const today = new Date();
    const day = weekday[today.getDay()];
    const dd = String(today.getDate()).padStart(2, "0");
    const mm = String(today.getMonth() + 1).padStart(2, "0");
    const yyyy = today.getFullYear();
    const h = String(today.getHours()).padStart(2, "0");
    const m = String(today.getMinutes()).padStart(2, "0");
    const s = String(today.getSeconds()).padStart(2, "0");

    const currentTime = `${h} giờ ${m} phút ${s} giây`;
    const currentDate = `${day}, ${dd}/${mm}/${yyyy}`;

    const display = `<span class="date">${currentDate} - ${currentTime}</span>`;
    document.getElementById("clock").innerHTML = display;
  }, 1000);
}
