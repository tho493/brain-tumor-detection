package com.tho493.brain_tumor_detect.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private final MutableLiveData<String> projectTitle;
    private final MutableLiveData<String> objectives;
    private final MutableLiveData<String> techniques;
    private final MutableLiveData<String> accuracy;
    private final MutableLiveData<String> accuracyDescription;

    public AboutViewModel() {
        projectTitle = new MutableLiveData<>();
        projectTitle.setValue("Dự án phát hiện khối u não");

        objectives = new MutableLiveData<>();
        objectives.setValue("Mục tiêu\nPhát triển hệ thống AI hỗ trợ phát hiện và phân loại khối u não từ ảnh MRI, giúp bác sĩ chẩn đoán nhanh chóng và chính xác hơn.");

        techniques = new MutableLiveData<>();
        techniques.setValue("Công nghệ sử dụng\nDeep Learning, Computer Vision, Python, Yolo, OpenCV, Flask");

        accuracy = new MutableLiveData<>();
        accuracy.setValue("Độ chính xác\nMô hình đạt độ chính xác 95% trong việc phát hiện và phân loại khối u não từ ảnh MRI.");

        accuracyDescription = new MutableLiveData<>();
        accuracyDescription.setValue("Dự án sử dụng mô hình Deep Learning tiên tiến để phân tích ảnh MRI nào, giúp phát hiện sớm các khối u não. Hệ thống có khả năng phân loại các loại u não khác nhau, được định nghĩa trực quan, giúp các bác sĩ có thêm công cụ hỗ trợ trong quá trình chẩn đoán và điều trị.");
    }

    public LiveData<String> getProjectTitle() {
        return projectTitle;
    }

    public LiveData<String> getObjectives() {
        return objectives;
    }

    public LiveData<String> getTechniques() {
        return techniques;
    }

    public LiveData<String> getAccuracy() {
        return accuracy;
    }

    public LiveData<String> getAccuracyDescription() {
        return accuracyDescription;
    }
}