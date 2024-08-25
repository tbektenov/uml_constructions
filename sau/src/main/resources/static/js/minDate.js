$(document).ready(() => {
    var today = new Date();
    var tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    var day = ("0" + tomorrow.getDate()).slice(-2);
    var month = ("0" + (tomorrow.getMonth() + 1)).slice(-2);
    var formattedDate = tomorrow.getFullYear() + "-" + month + "-" + day;

    $('#date').attr('min', formattedDate);
});