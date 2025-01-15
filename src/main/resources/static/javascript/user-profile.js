let userId = null;
let profileId = null;
let profileImages = [];
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
        console.log(images);
        projects.set(project.id, {
            project,
            images
        });
    }

    for (const key of projects.keys()) {
        const project = projects.get(key);
        console.log(project);
        $('#projectsContainer').append(`
           <div class="card me-2 scroll-card">
               <div class="card-body">
                <div class="row">
                    <div class="col-6">
                        <h5 class="card-title">${project.project.title}</h5>
                        <p class="card-text">${project.project.description}</p>
                    </div>
                    <div class="col-6">
                       <img
                            class="project-img"
                            src="data:image/png;base64,${project.project.featuredProjectImage.data}"
                            class=""
                            alt="Collage 4"
                         />
                    </div>
                </div>
               </div>
           </div>
        `);
    }

    offerings = await getUserProfileOfferings(userId, profileId);
    console.log(offerings);

    for (const offering of offerings) {
        $('#offerings-section').append(`
            <div class="col-2">
                <span class="badge bg-secondary">${offering}</span>
            </div>
        `);
    }

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