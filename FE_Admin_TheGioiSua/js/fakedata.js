var data = {
  labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6"],
  datasets: [
    {
      label: "Dữ liệu đầu tiên",
      fillColor: "rgba(255, 213, 59, 0.767), 212, 59)",
      strokeColor: "rgb(255, 212, 59)",
      pointColor: "rgb(255, 212, 59)",
      pointStrokeColor: "rgb(255, 212, 59)",
      pointHighlightFill: "rgb(255, 212, 59)",
      pointHighlightStroke: "rgb(255, 212, 59)",
      data: [20, 59, 90, 51, 56, 100],
    },
    {
      label: "Dữ liệu kế tiếp",
      fillColor: "rgba(9, 109, 239, 0.651)  ",
      pointColor: "rgb(9, 109, 239)",
      strokeColor: "rgb(9, 109, 239)",
      pointStrokeColor: "rgb(9, 109, 239)",
      pointHighlightFill: "rgb(9, 109, 239)",
      pointHighlightStroke: "rgb(9, 109, 239)",
      data: [48, 48, 49, 39, 86, 10],
    },
  ],
};
var ctxl = $("#lineChartDemo").get(0).getContext("2d");
var lineChart = new Chart(ctxl).Line(data);

var ctxb = $("#barChartDemo").get(0).getContext("2d");
var barChart = new Chart(ctxb).Bar(data);
