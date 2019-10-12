package eu.aimless.f1predictor.ui.predictions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;

import org.json.JSONObject;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.ApiRepository;

public class PredictionsFragment extends Fragment {

    private PredictionsViewModel predictionsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        predictionsViewModel =
                ViewModelProviders.of(this).get(PredictionsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_predictions, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.driverlist);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(mLayoutManager);

        predictionsViewModel.setCacheDir(getActivity().getCacheDir());
        predictionsViewModel.loadDrivers(recyclerView, getResources());

        return root;
    }
}