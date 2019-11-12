package eu.aimless.f1predictor.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.FirestoreHelper;

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

//    new FirestoreHelper().getPolePosition(raceid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//
//        @Override
//        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//            if(task.isSuccessful()) {
//                DocumentSnapshot document = task.getResult();
//
//                Map<String, Object> data = document.getData();
//                Map<String, Object> firstResult = ((List<Map<String, Object>>)data.get("QualifyingResults")).get(0);
//
//                String poleName = ((Map<String, Object>)firstResult.get("Driver")).get("familyName").toString();
//                String poleId = ((Map<String, Object>)firstResult.get("Driver")).get("driverId").toString();
//
//                poleText.setText(poleName);
//                putLogo(poleLogo, poleId);
//            }
//        }
//    });



    private void addData() {
        new FirestoreHelper().getConstructorPoints().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    //ADDING DATA
                    List<PieEntry> entries = new ArrayList<>();

                    Map<String, Object> data = document.getData();

                    Map<String, Integer> dataMutate = new HashMap<>();
                    for(Map.Entry<String, Object> entry : data.entrySet()) {
                        String constructor = entry.getKey();
                        Long points = (Long)entry.getValue();

                        dataMutate.put(constructor, points.intValue());
                    }

                    List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(dataMutate.entrySet());
                    sortedList.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

                    for(Map.Entry<String, Integer> entry : sortedList) {
                        String constructor = entry.getKey();
                        int points = entry.getValue();

                        Log.d("Test","Jaaaaa het is gelukt");

                        entries.add(new PieEntry(points, constructor));
                    }

                    PieDataSet dataset = new PieDataSet(entries, "Constructor points");

                    //ADDING COLORS
                    ArrayList<Integer> colors = new ArrayList<>();
                    colors.add(Color.rgb(0, 210, 190)); //mercedes
                    colors.add(Color.rgb(220, 0, 0)); //ferrari
                    colors.add(Color.rgb(25, 60 , 245)); //redbull
                    colors.add(Color.rgb(255, 135, 0)); //mclaren
                    colors.add(Color.rgb(235, 225, 0)); //renault
                    colors.add(Color.rgb(245, 150, 200)); //racing point
                    colors.add(Color.rgb(70, 155, 255)); //toro rosso
                    colors.add(Color.rgb(155, 0, 0)); //alfa romeo
                    colors.add(Color.rgb(230, 205, 125)); //haas
                    colors.add(Color.rgb(200, 200, 200)); //williams


                    dataset.setColors(colors);

                    //FILLING CHART WITH DATA
                    PieData pieData = new PieData(dataset);
                    mChart.setData(pieData);
                    mChart.invalidate();
                }
                else
                {
                    Log.d("hghg","nope neit gelukt jammer joh");
                }
            }
        });

//        entries.add(new PieEntry(695, "mercedes"));
//        entries.add(new PieEntry(479, "ferrari"));
//        entries.add(new PieEntry(366, "red bull"));
//        entries.add(new PieEntry(121, "mclaren"));
//        entries.add(new PieEntry(83, "renault"));
//        entries.add(new PieEntry(65, "racing point"));
//        entries.add(new PieEntry(64, "toro rosso"));
//        entries.add(new PieEntry(35, "alfa romeo"));
//        entries.add(new PieEntry(28, "haas"));
//        entries.add(new PieEntry(1, "williams"));



    }

}
