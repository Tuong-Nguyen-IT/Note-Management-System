package com.example.notemanagementsystem.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.notemanagementsystem.R;
import com.example.notemanagementsystem.model.Note;
import com.example.notemanagementsystem.model.Status;
import com.example.notemanagementsystem.ui.status.StatusViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class HomeFragment extends Fragment {
    private ArrayList<Status> dataStatus;
    private ArrayList<Note> dataNote;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        List<SliceValue> pieList = new ArrayList<>();
        dataStatus = new ArrayList<>();
        dataStatus = homeViewModel.getStatusArray();
        //homeViewModel.countStatus(dataStatus.get(0).getName())
        int dem=0;
        float sum = homeViewModel.sumStatus();
        while (dem < dataStatus.size()){
            float avg = (homeViewModel.countStatus(dataStatus.get(dem).getName())/sum)*100;
            pieList.add(new SliceValue((int)avg,color(dem)).setLabel(dataStatus.get(dem).getName()+" "+avg+" %"));
            dem++;
        }
        PieChartData pieData = new PieChartData(pieList);
        pieData.setHasLabels(true);

        PieChartView pieView = root.findViewById(R.id.piechart);
        pieView.setPieChartData(pieData);

        return root;

    }
    public int color(int i){
        int mau=1;
        switch (i){
            case 0:
                mau = Color.BLUE;
                break;
            case 1:
                mau = Color.RED;
                break;
            case 2:
                mau = Color.GRAY;
                break;
            case 3:
                mau = Color.GREEN;
                break;
            case 4:
                mau = Color.YELLOW;
                break;
            case 5:
                mau = Color.BLACK;
                break;
            default:
                mau = i;
        }
        return mau;
    }
}