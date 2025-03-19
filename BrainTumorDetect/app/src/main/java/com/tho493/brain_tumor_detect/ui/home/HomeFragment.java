package com.tho493.brain_tumor_detect.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tho493.brain_tumor_detect.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private Uri selectedImageUri;

    // Khởi tạo ActivityResultLauncher để chọn ảnh
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        selectedImageUri = data.getData();
                        homeViewModel.handleSelectedFile(selectedImageUri);
                        binding.imageViewSelected.setImageURI(selectedImageUri); // Hiển thị ảnh đã chọn
                        binding.imageViewSelected.setVisibility(View.VISIBLE); // Hiện ImageView
                    }
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupUI();
        observeViewModel();

        return root;
    }

    private void setupUI() {
        // Thiết lập sự kiện click cho khu vực tải lên
        binding.uploadArea.setOnClickListener(v -> openFilePicker());

        // Thiết lập nút bắt đầu
        binding.btnStart.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                homeViewModel.startDetection(selectedImageUri, getContext());
            } else {
                Toast.makeText(getContext(), "Vui lòng chọn ảnh trước", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeViewModel() {
        homeViewModel.getDetectionState().observe(getViewLifecycleOwner(), state -> {
            switch (state.getStatus()) {
                case INITIAL:
                    binding.btnStart.setEnabled(false);
                    binding.progressBar.setVisibility(View.GONE);
                    break;
                case FILE_SELECTED:
                    binding.btnStart.setEnabled(true);
                    binding.progressBar.setVisibility(View.GONE);
                    break;
                case PROCESSING:
                    binding.btnStart.setEnabled(false);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    binding.btnStart.setEnabled(true);
                    binding.progressBar.setVisibility(View.GONE);
                    showResult(state.getResult());
                    break;
                case ERROR:
                    binding.btnStart.setEnabled(true);
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), state.getError(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        pickImage.launch(intent);
    }

    private void showResult(String result) {
        // Hiển thị kết quả phát hiện
        Toast.makeText(getContext(), "Kết quả: " + result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}