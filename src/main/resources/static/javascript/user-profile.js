let userId = null;
let profileId = null;
let photos = [];

async function getUser() {
    const userResponse = await fetch(`http://localhost:8080/api/user?userName=${localStorage.getItem('userName')}`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    return await userResponse.json();
}

async function getUserProfile(data) {
    const userProfileResponse = await fetch(`http://localhost:8080/api/user/${data.id}/profile`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    return await userProfileResponse.json();
}

async function createUserProfile(data) {
    const createdProfileResponse = await fetch(`http://localhost:8080/api/user/${data.id}/profile`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            description: "",
            title: ""
        })
    });
    return await createdProfileResponse.json();
}

async function getUserProfilePhotos(userId, profileId) {
    const userPhotosResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/image`, {
        method: "GET"
    });
    return await userPhotosResponse.json();
}

async function userProfile() {
    const userResponse = await getUser();
    let userProfileJson = await getUserProfile(userResponse);

    if (!userProfileJson.id) {
       userProfileJson = await createUserProfile(userResponse);
    }
    profileId = userProfileJson.id;
    userId = userResponse.id;
    photos = await getUserProfilePhotos(userId, profileId);

    for (let photo of photos) {
        document.getElementById(photo.elementId).src = `data:image/png;base64,${photo.data}`;
    }

    $('#description').text(`${userProfileJson.description}`);
    $('#name').text(`${userResponse.username}`);
    userResponse.phone ? $('#phone').text(`${userResponse.phone}`) : null;
    userResponse.email ? $('#email').text(`${userResponse.email}`) : null;
}

async function handleImageChange(event, elementId) {
    const file = event.target.files[0];

    if (file) {
        const imageUploadJson = await updateImage(file, elementId);
        console.log(imageUploadJson);
        const reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById(elementId).src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
}

async function updateImage(file, elementId) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('profileImage', JSON.stringify({
        'name': file.name,
        'elementId': elementId
    }));

    const oldImage = photos.find(photo => photo.elementId === elementId)
    const imageUploadResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/image/${oldImage.id}`, {
        method: 'PUT',
        body: formData
    });
    return await imageUploadResponse.json();
}

$(document).ready(() => {
    $('#welcomeBackModal').on('hide.bs.modal', userProfile);
    $('#userModal').on('hide.bs.modal', userProfile);
})