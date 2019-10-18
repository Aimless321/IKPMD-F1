package eu.aimless.f1predictor.ui.predictions;

import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.ApiRepository;

public class PredictionsFragment extends Fragment implements View.OnClickListener {

    private PredictionsViewModel predictionsViewModel;
    private RecyclerView recyclerView;

    //Save prediction
    private int currentPrediction = 0;
    private ArrayList<String> predictions = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        predictionsViewModel =
                ViewModelProviders.of(this).get(PredictionsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_predictions, container, false);

        root.findViewById(R.id.saveButton).setOnClickListener(view -> saveButtonPressed(view));
        root.findViewById(R.id.resetButton).setOnClickListener(view -> resetButtonPressed(view));

        recyclerView = root.findViewById(R.id.driverlist);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(mLayoutManager);

        predictionsViewModel.setCacheDir(getActivity().getCacheDir());
        predictionsViewModel.loadDrivers(this, recyclerView, getResources());

        return root;
    }

    public void saveButtonPressed(View view) {
        Log.d("Prediction", "Save button pressed");
    }

    public void resetButtonPressed(View view) {
        predictionsViewModel.loadDrivers(this, recyclerView, getResources());
    }

    @Override
    public void onClick(View view) {
        if(currentPrediction >= 10) {
            return;
        }

        view.setClickable(false);

        int itemPosition = recyclerView.getChildLayoutPosition(view);
        JSONArray listData = predictionsViewModel.getDriverData().getValue();

        try {
            JSONObject driver = listData.getJSONObject(itemPosition);
            Log.d("Prediction", currentPrediction + ": " + driver.getString("driverId"));

            TextView predictionText = view.findViewById(R.id.predictionNum);
            predictionText.setText(String.valueOf(currentPrediction+1));
            predictionText.setVisibility(View.VISIBLE);

            predictions.add(driver.getString("driverId"));
            currentPrediction++;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}