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
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { textView.setText(s);
            }
        });

        setupPieChart();
        setData(0);
        setupButtonListener();

        return root;
    }

    //pie chart code down below

    protected void setupPieChart(){
        mChart = (PieChart) root.findViewById(R.id.chart);
        Description d = new Description();
        d.setText("PieChart");
        d.setTextSize(20f);
        mChart.setDescription(d);
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private void setData(int aantal) {
        currentEcts = aantal;
        List<PieEntry> yValues = new ArrayList<>();
        List<PieEntry> xValues = new ArrayList<>();

        yValues.add(new PieEntry(aantal, 0));
        xValues.add(new PieEntry(aantal, "Behaalde ECTS"));

        yValues.add(new PieEntry(60 - currentEcts, 1));
        xValues.add(new PieEntry(60 - aantal, "Resterende ECTS"));

        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        if (currentEcts <10) {
            colors.add(Color.rgb(244,81,30));
        } else if (currentEcts < 40){
            colors.add(Color.rgb(235,0,0));
        } else if  (currentEcts < 50) {
            colors.add(Color.rgb(253,216,53));
        } else {
            colors.add(Color.rgb(67,160,71));
        }
        colors.add(Color.rgb(255,0,0));

        PieDataSet dataSet = new PieDataSet(yValues, "ECTS");
        dataSet.setColors(colors);//colors);

        // PieDataSet set = new PieDataSet(xValues, "Election Results");

        PieData data = new PieData(dataSet);
        mChart.setData(data); // bind dataset aan chart.
        mChart.invalidate();  // Aanroepen van een redraw
        //Log.d("aantal =", ""+currentEcts);
    }


    protected void setupButtonListener() {
        Button fab = (Button) root.findViewById(R.id.plusTweeTest);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentEcts < MAX_ECTS) {
                    setData(currentEcts += 5);
                } else {
                    setData(currentEcts = 0);
                }
            }
        });
    }
}
