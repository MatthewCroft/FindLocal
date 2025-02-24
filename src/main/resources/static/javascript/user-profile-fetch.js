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

async function updateProfileImage(file, elementId, images) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('profileImage', JSON.stringify({
        'name': file.name,
        'elementId': elementId
    }));

    const oldImage = images.find(photo => photo.elementId === elementId)
    const imageUploadResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/image/${oldImage.id}`, {
        method: 'PUT',
        body: formData
    });
    return await imageUploadResponse.json();
}

async function addUserProjectImage(file, projectId) {
    const formData = new FormData();
    formData.append('file', file);

    const addUserProjectImageResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/project/${projectId}/image`, {
        method: "POST",
        body: formData
    });

    return await addUserProjectImageResponse.json();
}

async function createUserProject(title, description) {
   const userProjectCreateResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/project`, {
       method: 'POST',
       headers: {
           'Content-Type': 'application/json'
       },
       body: JSON.stringify({
           title,
           description
       })
   })

    return await userProjectCreateResponse.json();
}

async function updateFeaturedImage(file, projectId) {
    const formData = new FormData();
    formData.append('file', file);

   const featuredImageResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/project/${projectId}/image/featured`, {
       method: 'POST',
       body: formData
   });

   return await featuredImageResponse.json();
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

//todo: create offering
async function createUserProfileOffering(offer, profileId) {
    const userOfferingCreateResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/offering`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            offer
        })
    });

    return await userOfferingCreateResponse.json();
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
    const userProfileProjectImageResponse = await fetch(`http://localhost:8080/api/user/${userId}/profile/${profileId}/project/${projectId}/image`, {
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