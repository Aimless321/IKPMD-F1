package eu.aimless.f1predictor.ui.home;

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

import android.graphics.Color;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import eu.aimless.f1predictor.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;

    private PieChart mChart;
    public static final int MAX_ECTS = 60;
    public static int currentEcts = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        setupPieChart();
        addData();

        return root;
    }

    //pie chart code down below

    protected void setupPieChart(){
        mChart = (PieChart) root.findViewById(R.id.chart);
        Description d = new Description();
        d.setTextSize(20f);
        d.setText("");
        mChart.setDescription(d);
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private void addData() {
        //ADDING DATA
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(220, "mercedes"));
        entries.add(new PieEntry(190, "ferrari"));
        entries.add(new PieEntry(155, "red bull"));
        entries.add(new PieEntry(60, "mclaren"));
        entries.add(new PieEntry(60, "renault"));
        entries.add(new PieEntry(60, "haas"));
        entries.add(new PieEntry(60, "racing point"));
        entries.add(new PieEntry(60, "toro rosso"));
        entries.add(new PieEntry(60, "alfa romeo"));
        entries.add(new PieEntry(60, "williams"));

        PieDataSet dataset = new PieDataSet(entries, "Constructor points");

        //ADDING COLORS
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(0, 210, 190)); //mercedes
        colors.add(Color.rgb(220, 0, 0)); //ferrari
        colors.add(Color.rgb(25, 60 , 245)); //redbull
        colors.add(Color.rgb(255, 135, 0)); //mclaren
        colors.add(Color.rgb(235, 225, 0)); //renault
        colors.add(Color.rgb(230, 205, 125)); //haas
        colors.add(Color.rgb(245, 150, 200)); //racing point
        colors.add(Color.rgb(70, 155, 255)); //toro rosso
        colors.add(Color.rgb(155, 0, 0)); //alfa romeo
        colors.add(Color.rgb(200, 200, 200)); //williams

        dataset.setColors(colors);

        //FILLING CHART WITH DATA
        PieData data = new PieData(dataset);
        mChart.setData(data);
        mChart.invalidate();
    }

}
