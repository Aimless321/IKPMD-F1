package eu.aimless.f1predictor.ui.races;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.FirestoreHelper;
import eu.aimless.f1predictor.repository.model.Race;
import eu.aimless.f1predictor.ui.raceDetails.RaceDetailActivity;

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
                goToDetailScreen((Race)mListView.getItemAtPosition(position));
            }
        }
        );

        FirestoreHelper firestore = new FirestoreHelper();

        firestore.getRaces().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    for(DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Race race = document.toObject(Race.class);
                        Log.d("RaceModels", race.getRaceName());
                        if(Integer.parseInt(race.getRound()) < 20) {
                            raceModels.add(race);
                        }
                    }

                    raceModels.sort(new Comparator<Race>() {
                        @Override
                        public int compare(Race race, Race race1) {
                            Integer round = Integer.valueOf(race.getRound());
                            Integer round1 = Integer.valueOf(race1.getRound());
                            return round.compareTo(round1);
                        }
                    });

                    mAdapter = new RaceListAdapter(getContext(), 0, raceModels);
                    mListView.setAdapter(mAdapter);
                }
            }
        });

        return root;
    }


    private void goToDetailScreen(Race race) {
        Intent intent = new Intent(getActivity(), RaceDetailActivity.class);
        Bundle b = new Bundle();

        b.putInt("raceId", Integer.valueOf(race.getRound()));
        intent.putExtras(b);
        startActivity(intent);
    }

}