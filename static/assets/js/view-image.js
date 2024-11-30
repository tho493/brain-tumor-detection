let zoomLevel = 1;
function openModal(imageSrc, title) {
        const modal = document.getElementById("modal");
        const modalImage = document.getElementById("modal-image");
        const modalTitle = document.getElementById("modal-title");
        modalImage.src = imageSrc;
        modalTitle.textContent = title;
        modal.style.display = "flex";
        zoomLevel = 1;
        modalImage.style.transform = `scale(${zoomLevel})`;
    }

function closeModal(event) {
    const modal = document.getElementById("modal");
    const close_modal = document.getElementById("close_modal");
    const modalContent = document.getElementById("modal-content");
    if (event.target === modal || event.target === close_modal) {
        modalContent.style.animation = "zoomOut 0.3s forwards";

        setTimeout(() => {
            modal.style.display = "none";
            modalContent.style.animation = "";
        }, 250);
    }
}

function zoomIn() {
    const modalImage = document.getElementById("modal-image");
    zoomLevel += 0.1;
    modalImage.style.transform = `scale(${zoomLevel})`;
}

function zoomOut() {
    const modalImage = document.getElementById("modal-image");
    if (zoomLevel > 1) {
        zoomLevel -= 0.1;
        modalImage.style.transform = `scale(${zoomLevel})`;
    }

    
}