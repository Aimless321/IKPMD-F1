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
                Toast t = Toast.makeText(getContext(), "Click" + position, Toast.LENGTH_LONG);
                t.show();

                goToDetailScreen((Race)mListView.getItemAtPosition(position));
            }
        }
        );

        //DUMMY DATA
        FirestoreHelper firestore = new FirestoreHelper();

        firestore.getRaces().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    for(DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Race race = document.toObject(Race.class);
                        Log.d("RaceModels", race.getRaceName());
                        raceModels.add(race);
                    }

                    mAdapter = new RaceListAdapter(getContext(), 0, raceModels);
                    mListView.setAdapter(mAdapter);
                }
            }
        });

//        raceModels.add(new Race("09-6-2019", "Canada", 7, 2019, "06:10", "onbekende URL"));
//        raceModels.add(new Race("26-5-2019", "Monaco", 6, 2019, "14:10", "onbekende URL"));
//        raceModels.add(new Race("12-5-2019", "Spain", 5, 2019, "15:10", "onbekende URL"));
//        raceModels.add(new Race("28-4-2019", "Azerbaijan", 4, 2019, "14:10", "onbekende URL"));
//        raceModels.add(new Race("14-4-2019", "China", 3, 2019, "09:10", "onbekende URL"));
//        raceModels.add(new Race("31-3-2019", "Bahrain", 2, 2019, "18:10", "onbekende URL"));
//        raceModels.add(new Race("17-3-2019", "Australia", 1, 2019, "06:10", "onbekende URL"));

        return root;
    }


    private void goToDetailScreen(Race race) {
        Intent intent = new Intent(getActivity(), RaceDetailActivity.class);
        Bundle b = new Bundle();

        b.putInt("raceId", Integer.valueOf(race.getRound()));
        intent.putExtras(b);
        startActivity(intent);
    }

//    Race race = getItem(position);
//        vh.raceName.setText(race.getRaceName());
//        vh.round.setText(String.valueOf(race.getRound()));
//        vh.date.setText(race.getDate());
//        return convertView;


}