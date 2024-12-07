<h1 align="center">Brain tumor detection</h1>

<p align="center">
    Project phát hiện bệnh trên ảnh chụp cộng hưởng từ MRI.
    <br />
    <br />
    <a href="https://github.com/tho493/brain-tumor-detection/issues">Report Bug</a>
    ·
    <a href="https://github.com/tho493/brain-tumor-detection/pulls">Request Feature</a>
    </p>
</p>

## Demo

[App Demo](https://brain-tumor-detect.tho493.id.vn)

## Bắt đầu

### Điều kiện cần có

Yêu cầu python phiên bản 3.12.

Môi trường đầy đủ các thành phần:

- Flask 3.0.3
- Flask-Cors 4.0.1
- Ultralytics 8.3.34
- Opencv-python 4.10.0.84

Hoặc cài đặt các thư viện theo cú pháp sau:

```bash
$ python -m pip install -r requirements.txt
```

### Cài đặt

Clone project về thư mục của bạn, project tổng quan sẽ như sau:

```
YOUR_PROJECT_DIR
└── app
    ├── app.py                     // Web server
    ├── best.pt                    // Custom data yolov11
    ├── requirements.txt           // All library require
    ├── template
    |   ├── index.html             // App front-end
    |   └── 404.html               // Error handle 404
    └── static
        └── assets
            ├── css
            |   ├── 404.css
            |   ├── base.css
            |   ├── grid.css
            |   ├── style.css
            |   └── style.css
            ├── img                // Folder image template
            └── js
                ├── index.js       // App functions
                └── view-image.js  // Function view image
```

### Khởi chạy server

```bash
$ cd DIR_TO_YOUR_PROJECT/
$ python app.py
```

### Sử dụng

- Mở trình duyệt và truy cập `https://localhost:5000`
- Click chọn ảnh và chọn ảnh chụp cộng hưởng từ trên máy
- Nhấn nút bắt đầu và chờ quá trình phân tích thành công

### Thư viện bên ngoài

- [Ultralytics](https://www.ultralytics.com/)
- [Flask](http://flask.pocoo.org/)
- [Google Fonts](https://fonts.google.com/)
- [AOS Animation](https://michalsnik.github.io/aos/)

### Note

Ứng dụng này được phát triển cho mục đích nghiên cứu. Được giám sát bởi ThS Phạm Thị Hường

Nhóm tác giả bao gồm:

- Nguyễn Chí Thọ (Leader) - [Facebook](https://facebook.com/tho493)
- Vũ Thị Bắc (Báo cáo) - [Zalo](https://zalo.me/0367456697)
- Tăng Quang Nghĩa (Front-end) - [Facebook](https://www.facebook.com/cuchuoi.votinh.355)

## Mics

- Bộ data ở [kaggle](https://www.kaggle.com/datasets/ammarahmed310/labeled-mri-brain-tumor-dataset?resource=download)
- [Yoboflow](https://app.roboflow.com/yolo-yvyq9/brain-tumor-detection-dataset-tkxin/deploy)
