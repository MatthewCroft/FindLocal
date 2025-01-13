let userId = null;
let profileId = null;
let profileImages= [];
let offerings = [];
let projects = new Map();

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

async function getUserProfileOfferings(userId, profileId) {
    const userOfferingsResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/offering`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return await userOfferingsResponse.json();
}

async function getUserProfileProjects(userId, profileId) {
    const userProfileProjectsResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/project`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return await userProfileProjectsResponse.json();
}

async function getUserProfileProjectsImages(userId, profileId, projectId) {
    const userProfileProjectImageResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/image`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return await userProfileProjectImageResponse.json();
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
    profileImages = await getUserProfilePhotos(userId, profileId);

    for (const image of profileImages) {
        document.getElementById(image.elementId).src = `data:image/png;base64,${image.data}`;
    }

    //todo: get user projects(and photos), also get user offerings
    const userProfileProjects = await getUserProfileProjects(userId, profileId);
    console.log(userProfileProjects);
    for (const project of userProfileProjects) {
        const images = await getUserProfileProjectsImages(userId, profileId, project.id);
        projects.set(project.id, JSON.stringify({
            project,
            images
        }));
    }
    console.log(projects);
    offerings = await getUserProfileOfferings(userId, profileId);
    console.log(offerings);

    $('#description').text(`${userProfileJson.description}`);
    $('#name').text(`${userResponse.username}`);
    userResponse.phone && $('#phone').text(`${userResponse.phone}`);
    userResponse.email && $('#email').text(`${userResponse.email}`);
}

//async function addProjectFeaturedImage

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
    $('#projectAdd').on('click', () => $('#userProjectsModal').modal('show'));
})