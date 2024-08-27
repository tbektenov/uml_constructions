$(document).ready(() => {

    /**
     * Sets the minimum selectable date in an HTML input element with the ID 'date'
     * to be the date of tomorrow. The date is formatted as 'YYYY-MM-DD'.
     */
    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);

    var day = ("0" + tomorrow.getDate()).slice(-2);
    var month = ("0" + (tomorrow.getMonth() + 1)).slice(-2);
    var formattedDate = tomorrow.getFullYear() + "-" + month + "-" + day;

    $('#date').attr('min', formattedDate);

});