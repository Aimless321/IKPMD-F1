package eu.aimless.f1predictor.ui.races;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.model.Race;

public class RaceListFragment extends Fragment {

    private RaceListViewModel raceListViewModel;
    private ListView mListView;
    private RaceListAdapter mAdapter;
    private List<Race> raceModels = new ArrayList<>();

    //code zoals "courselistactivity" in college 3
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        raceListViewModel = ViewModelProviders.of(this).get(RaceListViewModel.class);

        View root = inflater.inflate(R.layout.fragment_racelist, container, false);

        mListView = root.findViewById(R.id.race_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                Toast t = Toast.makeText(getContext(), "Click" + position, Toast.LENGTH_LONG);
                t.show();
            }
        }
        );

        //DUMMY DATA
        raceModels.add(new Race("25-7-2019", "Germany", 6, 2019, "14:10", "onbekende URL"));
        raceModels.add(new Race("17-5-2019", "Australia", 1, 2019, "07:10", "onbekende URL"));

        mAdapter = new RaceListAdapter(getContext(), 0, raceModels);
        mListView.setAdapter(mAdapter);

        return root;
    }
}