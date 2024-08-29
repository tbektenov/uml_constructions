$(document).ready(() => {
    /**
     * Sets the minimum selectable date in an HTML input element with the ID 'date'
     * to be the date of today.
     */
    var today = new Date();

    var day = ("0" + today.getDate()).slice(-2);
    var month = ("0" + (today.getMonth() + 1)).slice(-2);
    var formattedDate = today.getFullYear() + "-" + month + "-" + day;

    $('#date').attr('min', formattedDate);

});