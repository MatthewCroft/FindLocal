let userId = null;
let profileId = null;
let profileImages= [];
let offerings = [];
let projects = new Map();
let featuredImage = null;
let projectImages = [];

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

function loadImage(imageData, elementId) {
    document.getElementById(elementId).src = imageData;
}

function handleFeaturedImageChange(event, elementId) {
    const file = event.target.files[0];
    console.log(file);

    $('#featuredImageContainer').removeClass('d-none');
    if (file) {
        loadImage(URL.createObjectURL(file), elementId);
        featuredImage = file;
    }
}

function handleProjectImagesChange(event) {
   const files = event.target.files;
    $('#projectImagesContainer').addClass('d-none');
    $(`#projectImage-0`).addClass('d-none');
    $(`#projectImage-1`).addClass('d-none');
    $(`#projectImage-2`).addClass('d-none');
    $(`#projectImage-3`).addClass('d-none');

   if (files.length > 0) {
       $('#projectImagesContainer').removeClass('d-none');
       projectImages = [...event.target.files];
   }

   for (let i = 0; i < files.length && i < 4; i++) {
       $(`#projectImage-${i}`).removeClass('d-none');
       loadImage(URL.createObjectURL(files[i]), `projectImage-${i}`);
   }
}

async function handleImageChange(event, elementId) {
    const file = event.target.files[0];

    if (file) {
        const imageUploadJson = await updateProfileImage(file, elementId, projectImages);
        console.log(imageUploadJson);
        loadImage(imageUploadJson.data, elementId);
    }
}

async function addUserProject(event) {
    event.preventDefault();
    console.log("here");
    const projectTitle = $('#title').val();
    const projectDescription = $('#projectDescription').val();

    const createdUserProject = await createUserProject(projectTitle, projectDescription);

    featuredImage && await updateFeaturedImage(featuredImage, createdUserProject.id);
    for (const image of projectImages) {
        const response = await addUserProjectImage(image, createdUserProject.id);
        console.log(response);
    }
}

$(document).ready(() => {
    $('#welcomeBackModal').on('hide.bs.modal', userProfile);
    $('#userModal').on('hide.bs.modal', userProfile);
    $('#projectAdd').on('click', () => $('#userProjectsModal').modal('show'));
    $('#userProjectForm').on('submit', addUserProject);
})