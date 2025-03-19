package com.tho493.brain_tumor_detect.ui.home;

import static androidx.core.content.ContextCompat.startActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tho493.brain_tumor_detect.MainActivity;
import com.tho493.brain_tumor_detect.R;
import com.tho493.brain_tumor_detect.ResultActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {

    public enum Status {
        INITIAL,
        FILE_SELECTED,
        PROCESSING,
        SUCCESS,
        ERROR
    }

    public static class DetectionState {
        private final Status status;
        private final String result;
        private final String error;

        public DetectionState(Status status, String result, String error) {
            this.status = status;
            this.result = result;
            this.error = error;
        }

        public Status getStatus() {
            return status;
        }

        public String getResult() {
            return result;
        }

        public String getError() {
            return error;
        }
    }

    private final MutableLiveData<DetectionState> detectionState;

    public HomeViewModel() {
        detectionState = new MutableLiveData<>(new DetectionState(Status.INITIAL, null, null));
    }

    public LiveData<DetectionState> getDetectionState() {
        return detectionState;
    }

    public void handleSelectedFile(Uri uri) {
        detectionState.setValue(new DetectionState(Status.FILE_SELECTED, null, null));
    }

    public void startDetection(Uri imageUri, Context context) {
        // Set state to processing
        detectionState.setValue(new DetectionState(Status.PROCESSING, null, null));

        new Thread(() -> {
            try {
                // Chuyển đổi ảnh thành chuỗi Base64
                String b64_str = convertImageToBase64(imageUri, context);
                String fileName = imageUri.getLastPathSegment();

                // Gửi yêu cầu đến server
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("fileName", fileName);
                jsonBody.put("src", b64_str);

                // Gửi yêu cầu POST
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(
                        MediaType.parse("application/json"),
                        jsonBody.toString()
                );

                Request request = new Request.Builder()
                        .url("http://192.168.1.44:5000/upload")
                        .post(requestBody)
                        .build();

                // Xử lý phản hồi
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        detectionState.postValue(new DetectionState(Status.ERROR, null, "Lỗi kết nối: " + e.getMessage()));
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseBody = response.body().string();

                            JSONObject jsonResponse;
                            try {
                                jsonResponse = new JSONObject(responseBody);
                                String result = jsonResponse.getString("data");

                                detectionState.postValue(new DetectionState(Status.SUCCESS, result, "Thành công."));

                                // Khởi chạy ResultActivity và truyền dữ liệu
                                Intent intent = new Intent(context, ResultActivity.class);
                                intent.putExtra("result", result);
                                context.startActivity(intent);

                            } catch (JSONException e) {
                                detectionState.postValue(new DetectionState(Status.ERROR, null, "Lỗi JSON: " + e.getMessage()));
                            }

                        } else {
                            detectionState.postValue(new DetectionState(Status.ERROR, null, "Lỗi: " + response.code()));
                        }
                    }
                });
            } catch (JSONException e) {
                detectionState.postValue(new DetectionState(Status.ERROR, null, "Lỗi JSON: " + e.getMessage()));
            }
        }).start();
    }

    // Phương thức chuyển đổi ảnh thành chuỗi Base64
    private String convertImageToBase64(Uri imageUri, Context context) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            byte[] imageBytes = new byte[inputStream.available()];
            inputStream.read(imageBytes);
            inputStream.close();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}