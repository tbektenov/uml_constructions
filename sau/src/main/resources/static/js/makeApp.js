// script.js

// Get the modal element
var modal = document.getElementById("modal");

// Get the button that opens the modal
var openModalButton = document.getElementById("openModalButton");

// Get the <span> element that closes the modal
var closeButton = document.querySelector(".close");

// Get the search button
var searchButton = document.getElementById("searchButton");

// When the user clicks the button, open the modal 
openModalButton.onclick = function() {
    modal.classList.add("showModal");
}

// When the user clicks on <span> (x), close the modal
closeButton.onclick = function() {
    modal.classList.remove("showModal");
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.classList.remove("showModal");
    }
}

// Handling the search button click (just an alert for demonstration)
// searchButton.onclick = function() {
//     alert("Search button clicked!");
//     modal.classList.remove("showModal"); // Close modal after action
// }
