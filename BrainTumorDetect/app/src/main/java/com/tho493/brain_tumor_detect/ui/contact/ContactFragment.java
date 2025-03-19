package com.tho493.brain_tumor_detect.ui.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tho493.brain_tumor_detect.databinding.FragmentContactBinding;

public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Khởi tạo ViewModel
        ContactViewModel contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        // Tạo binding cho fragment
        binding = FragmentContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Thiết lập các TextView với dữ liệu từ ViewModel
        contactViewModel.getTitle().observe(getViewLifecycleOwner(), binding.contactTitle::setText);
        contactViewModel.getAddress().observe(getViewLifecycleOwner(), binding.address::setText);
        contactViewModel.getPhone().observe(getViewLifecycleOwner(), binding.phone::setText);
        contactViewModel.getEmail().observe(getViewLifecycleOwner(), binding.email::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Giải phóng binding để tránh rò rỉ bộ nhớ
        binding = null;
    }
}