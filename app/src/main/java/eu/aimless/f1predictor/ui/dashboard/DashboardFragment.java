package eu.aimless.f1predictor.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.model.Race;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ListView mListView;
    private RaceListAdapter mAdapter;
    private List<Race> raceModels = new ArrayList<>();

    //code zoals "courselistactivity" in college 3
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.race_list_view);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { textView.setText(s);
            }
        });

        mListView = (ListView)findViewById(R.id.race_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                Toast t = Toast.makeText(DashboardFragment.this, "Click" + position, Toast.LENGTH_LONG);
                t.show();
            }
        }
        );

        //DUMMY DATA
        raceModels.add(new Race("25-7-2019", "Germany", 6, 2019, "14:10", "onbekende URL"));
        raceModels.add(new Race("17-5-2019", "Australia", 1, 2019, "07:10", "onbekende URL"));

        return root;
    }
}