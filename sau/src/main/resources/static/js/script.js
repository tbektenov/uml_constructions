document.addEventListener('DOMContentLoaded', function () {
    function openPopup() {
        document.getElementById("appointment-popup").style.display = "flex";
    }

    function closePopup() {
        document.getElementById("appointment-popup").style.display = "none";
    }

    window.openPopup = openPopup;
    window.closePopup = closePopup;
});
