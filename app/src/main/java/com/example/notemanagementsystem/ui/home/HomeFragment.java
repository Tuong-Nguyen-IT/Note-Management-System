package com.example.notemanagementsystem.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.notemanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        float Processing, Pending, Done;
        Processing = 50;
        Pending = 25;
        Done = 25;
        List<SliceValue> pieList = new ArrayList<>();
        pieList.add(new SliceValue(Processing,Color.BLUE).setLabel("Processing: "+Processing+"%"));
        pieList.add(new SliceValue(Pending,Color.RED).setLabel("Pending: "+Pending+"%"));
        pieList.add(new SliceValue(Done,Color.GRAY).setLabel("Done: "+Done+"%"));

        PieChartData pieData = new PieChartData(pieList);
        pieData.setHasLabels(true);

        PieChartView pieView = root.findViewById(R.id.piechart);
        pieView.setPieChartData(pieData);
        return root;
    }
}