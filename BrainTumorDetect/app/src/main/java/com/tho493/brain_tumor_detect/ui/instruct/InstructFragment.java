package com.tho493.brain_tumor_detect.ui.instruct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tho493.brain_tumor_detect.databinding.FragmentInstructBinding;

public class InstructFragment extends Fragment {

    private FragmentInstructBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InstructViewModel galleryViewModel =
                new ViewModelProvider(this).get(InstructViewModel.class);

        binding = FragmentInstructBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textInstruct;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}