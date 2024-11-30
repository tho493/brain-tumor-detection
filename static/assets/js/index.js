const input = document.querySelector("#fileInput");

function add_image(src_image, file_name, count) {
    const variableHtml = `<div class="form-select__image">
        <img class="selectedImage" id="selectedImage-${count}" src="${src_image}" alt="${file_name}" onclick="openModal(this.src, '${file_name}')">
        <span class="file-name">${file_name}</span>
        </div>`;

    document.getElementById("form-selected").insertAdjacentHTML("beforeend", variableHtml);
}

input.addEventListener('change', (e) => {
    const files = e.target.files;
    document.getElementById("form-selected").innerHTML = "";

    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();

        reader.onload = (event) => {
            const src = event.target.result;
            add_image(src, file.name, i);
        };
        reader.readAsDataURL(file);
    }
});

// Show result

const btnShow = document.querySelector("#show_result");
const result = document.querySelector(".result");

btnShow.onclick = () => {
    const skeletonHtml = `
    <div class="result-form__skeleton">
        <div class="result-skeleton__heading"></div>
        <div class="result-skeleton__container">
            <div class="result-items__container">
                <div class="result-group__img-skeleton">
                    <div class="img"></div>
                </div>
                <div class="result-group__info">
                    <div class="result-group__skeleton"></div>
                    <div class="result-group__skeleton"></div>
                </div>
            </div>
        </div>
    </div>`;

    if(skeletonHtml) {
        result.innerHTML = skeletonHtml;
        result.classList.add('active');
        const selectedImages = document.querySelectorAll('.selectedImage');
        const imagesData = [];

        selectedImages.forEach((img, index) => {
            const imgData = {
                fileName: img.alt,
                src: img.src
            };
            imagesData.push(imgData);
        });
        const fetchData = async () => {
            await new Promise(resolve => setTimeout(resolve, 2000));
            try {
                const response = await fetch('/', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(imagesData),
                });
                const data = await response.json();

                result.classList.remove('active');
                await new Promise(resolve => setTimeout(resolve, 300));
                result.classList.add('active');
                const resultHtml = `<div class="result-form">
                                    <h1>Kết quả</h1>
                                    <div class="result-container" id="result-container"></div>
                                    </div>
                                    `;
                result.innerHTML = resultHtml;

                if(data["error"] === 0){
                    data["data"].forEach((item, index) => {
                        const resultHtmlItem = `
                                            <div class="result-items">
                                                <div class="result-group__img">
                                                    <img src="${item.b64_str}" alt="Image ${index + 1}" onclick="openModal(this.src,'${item.fileName}')">
                                                    <span class="file_name">${item.fileName}</span>
                                                    </div>
                                                <div class="result-group__info">
                                                    <p>Phân loại: ${item.cls_name}</p>
                                                    <p>Tỉ lệ chính xác: ${(item.conf * 100).toFixed(1)}%</p>
                                                </div>
                                            </div>
                                        `;
                        document.getElementById("result-container").insertAdjacentHTML("beforeend", resultHtmlItem);
                    });
                }
                else {
                    const resultHtmlItem = `
                                        <div class="result-items">
                                            <div class="result-group__info">
                                                <p style="color: red;">Error code: ${data["error"]}</p>
                                                <p>Message: ${data["message"] == "" ? "Không tìm thấy ảnh nào" : data["message"]}</p>
                                            </div>
                                        </div>
                                    `;
                    document.getElementById("result-container").insertAdjacentHTML("beforeend", resultHtmlItem);
                }

            } catch (error) {
                console.error('Error:', error);
            }
        };

        fetchData();
    }
}
