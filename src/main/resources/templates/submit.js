// Get the form data from an HTML form with id "myForm"
const form = document.getElementById("myForm");
const body = document.getElementById("body");
const submitButton = document.getElementById("submitButton");
const div = document.getElementById("resultDiv");

// URL where you want to send the request
const url = "/api/v1/student/registerNewStudent";

body.addEventListener("click", (event) => {
    if (event.target == submitButton){
        const formData = new FormData(form);
        event.preventDefault()

        // Create a fetch request
        fetch(url, {
            method: "POST", // You can change this to "GET" or other HTTP methods
            body: formData,
        })
            .then(response => {
                if (response.ok) {
                    return response.text(); // or response.json() if the server returns JSON
                } else {
                    throw new Error('Network response was not ok');
                }
            })
            .then(data => {
                // Handle the response data here
                div.innerHTML = div.innerHTML + " " + data;
            })
            .catch(error => {
                div.innerHTML  = "error";
            });
    }

})