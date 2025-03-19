package com.tho493.brain_tumor_detect.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tho493.brain_tumor_detect.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AboutViewModel aboutViewModel = new ViewModelProvider(this).get(AboutViewModel.class);

        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Thiết lập các TextView với dữ liệu từ ViewModel
        aboutViewModel.getProjectTitle().observe(getViewLifecycleOwner(), binding.textProjectTitle::setText);
        aboutViewModel.getObjectives().observe(getViewLifecycleOwner(), binding.textObjectives::setText);
        aboutViewModel.getTechniques().observe(getViewLifecycleOwner(), binding.textTechniques::setText);
        aboutViewModel.getAccuracy().observe(getViewLifecycleOwner(), binding.textAccuracy::setText);
        aboutViewModel.getAccuracyDescription().observe(getViewLifecycleOwner(), binding.textAccuracyDescription::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}