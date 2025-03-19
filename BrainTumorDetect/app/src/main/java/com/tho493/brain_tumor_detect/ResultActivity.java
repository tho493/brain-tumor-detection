package com.tho493.brain_tumor_detect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Lấy dữ liệu từ Intent
        String resultJson = getIntent().getStringExtra("result");

        // Hiển thị kết quả lên TextView
        TextView textViewResult = findViewById(R.id.textViewResult);
        String base64Image = null;

        if (resultJson != null) {
            try {
                // Phân tích mảng JSON
                JSONArray jsonArray = new JSONArray(resultJson);
                JSONObject jsonObject = jsonArray.getJSONObject(0); // Lấy đối tượng đầu tiên
                base64Image = jsonObject.getString("b64_str"); // Trích xuất chuỗi Base64

                String cls_name = jsonObject.getString("cls_name");
                double conf = jsonObject.getDouble("conf");

                DecimalFormat df = new DecimalFormat("#.#");
                String formattedConf = df.format(conf);

                textViewResult.setText("Dạng khối u: " + cls_name + "\nTỉ lệ chính xác: " + conf + "%");

            } catch (JSONException e) {
                e.printStackTrace();
                textViewResult.setText("Lỗi khi phân tích dữ liệu.");
            }
        }

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity hiện tại
            }
        });

        // Hiển thị ảnh từ chuỗi Base64
        ImageView imageViewResult = findViewById(R.id.imageViewResult);
        if (base64Image != null) {
            String base64 = base64Image.split(",")[1];
            Bitmap bitmap = decodeBase64ToBitmap(base64);
            imageViewResult.setImageBitmap(bitmap);
        }
    }

    // Phương thức chuyển đổi chuỗi Base64 thành Bitmap
    private Bitmap decodeBase64ToBitmap(String base64Str) {
        byte[] decodedString = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}