const existingUserModal= new bootstrap.Modal(document.getElementById('welcomeBackModal'));
const newUserModal = new bootstrap.Modal(document.getElementById('userModal'));

async function saveUser() {
    const username = $('#usernameInput').val().trim();
    const email = $('#emailInput').val().trim();

    if(!username || !email) {
        alert("Please fill in both fields.");
        return;
    }

    console.log("Username:", username);
    console.log("Email:", email);

    const response = await fetch('http://localhost:8080/api/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username,
            email: email
        })
    });

    const json = await response.json();
    localStorage.setItem('userName', json.username);
    newUserModal.hide();
}

function showGreetingModal() {
    if (!localStorage.getItem('userName')) {
        newUserModal.show();
    } else {
       existingUserModal.show();
        $("#welcomeBackMessage").text(`${localStorage.getItem('userName')}`);
        setTimeout(() => {
            existingUserModal.hide();
        }, 3000)
    }
}

$(document).ready(() => {
    showGreetingModal();
    $('#saveUserBtn').click(saveUser);
})