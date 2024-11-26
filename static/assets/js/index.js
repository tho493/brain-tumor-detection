const input = document.querySelector("#fileInput");

        function add_image(src_image, count) {
            const variableHtml = `<div class="form-select__image">
                <img class="selectedImage" id="selectedImage-${count}" src="${src_image}" alt="hinh-${count}">
                </div>`;

            document.getElementById("form-selected").insertAdjacentHTML("beforeend", variableHtml);
        }

        input.addEventListener('change', (e) => {
            const files = e.target.files;

            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                const reader = new FileReader();

                reader.onload = (event) => {
                    src = event.target.result;
                    add_image(src, i)
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
                    // const imgData = {
                    //     id: `selectedImage-${index}`,
                    //     src: img.src
                    // };
                    imagesData.push(img.src);
                });

                const fetchData = async () => {
                    try {
                        const response = await fetch('/', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(imagesData),
                        });
                        const data = await response.json();
                        // console.log(data);
                        var resultHtml = `<div class="result-form">
                                    <h1>Kết quả</h1>
                                    <div class="result-container">`
                        if(data["error"] == 0){
                            data["data"].forEach((item) => {
                                resultHtml += `
                                            <div class="result-items">
                                                <div class="result-group__img">
                                                    <img src="${item.b64_str}" alt="hinh-anh">
                                                </div>
                                                <div class="result-group__info">
                                                    <p>Class: ${item.cls_name}</p>
                                                    <p>Conf: ${item.conf}</p>
                                                </div>
                                            </div>
                                        `;
                            });
                            resultHtml += `</div>
                                    </div>`
                        }
                        else if (data["error"] == 2) {
                            resultHtml += `
                                <div class="result-items">
                                    <div class="result-group__info">
                                        <p>Error: ${data["error"]}</p>
                                        <p>Message: ${data["message"]}</p>
                                    </div>
                                </div>
                            `;
                        }
                        else {
                            resultHtml += `
                            <div class="result-items">
                                    <div class="result-group__info">
                                        <p>Không tìm thấy ảnh nào</p>
                                    </div>
                                </div>`
                        }
                        result.classList.remove('active');
                        result.innerHTML = resultHtml;
                        result.classList.add('active');
                        // Handle the response data as needed
                    } catch (error) {
                        console.error('Error:', error);
                    }
                };

                fetchData();
            }
        }
