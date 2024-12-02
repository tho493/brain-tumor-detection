from flask_cors import CORS, cross_origin
from flask import Flask, json, request, render_template
from ultralytics import YOLO
from ultralytics.utils.plotting import Annotator, colors
import numpy as np
import cv2
import base64
import os

dir_path = os.path.dirname(os.path.realpath(__file__))

# Config
model = YOLO(os.path.join(dir_path,'best.pt'))
app = Flask(__name__)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'
app.config['DEBUG'] = True
app.config['TESTING'] = True
app.config['PORT'] = 10000

def get_cv2_image_from_base64_string(b64str):
    encoded_data = b64str.split(',')[1]
    nparr = np.frombuffer(base64.b64decode(encoded_data), np.uint8)
    img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    return img

def get_base64_string_from_cv2_image(img):
    _, buffer = cv2.imencode('.jpg', img)
    jpg_as_str = base64.b64encode(buffer).decode('utf-8')
    return "data:image/jpeg;base64," + jpg_as_str


def detection_image(image):
    results = model.track(image, persist=False)
    length_detections = len(results[0].boxes)  
    if(length_detections > 0):
        # Gắn nhãn và lấy các tham số
        annotator = Annotator(image)
        conf = results[0].boxes.conf.cpu().tolist()
        boxes = results[0].boxes.xyxy.cpu()
        clss = results[0].boxes.cls.cpu().tolist()
        for box, cls in zip(boxes, clss):
            annotator.box_label(box, color=colors(int(cls), True))

        b64_str = get_base64_string_from_cv2_image(image)
        cls_name = results[0].names[cls]
        return [length_detections, b64_str, conf, cls_name]
    else: 
        b64_str = get_base64_string_from_cv2_image(image)
        return [length_detections, b64_str, [1], "Không phát hiện"]

@app.route('/',methods=['GET'])
def home():
    return render_template('index.html')

@app.route("/", methods=['POST'])
def read_root():
    decoded_data = request.data
    data = json.loads(decoded_data)
    if not data or data == []:
        return {"error": 1, "message": "No image detected"}
    try:
        response_data = {"error": 0, "message": "", "data": []}
        for i, item in enumerate(data):
            # Decode the base64-encoded image
            image = get_cv2_image_from_base64_string(item["src"])
            fileName = item["fileName"]
            # image = cv2.resize(image, (224, 224))
            length_detections, b64_str, conf, cls_name = detection_image(image)
            response_data["data"].append({"is_detected": length_detections, "fileName": fileName, "b64_str": b64_str, "conf": conf[0], "cls_name": cls_name})
    except Exception as e:
        return {"error": 2, "message": f"{str(e)}"}
    
    return response_data

if __name__ == '__main__':
    app.run(port=app.config['PORT'], use_reloader=False)
