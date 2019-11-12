package eu.aimless.f1predictor.ui.predictions;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    private View root;

    private PredictionsViewModel predictionsViewModel;
    private RecyclerView recyclerView;

    //Save prediction
    private int currentPrediction = 0;
    private ArrayList<String> predictions = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        predictionsViewModel =
                ViewModelProviders.of(this).get(PredictionsViewModel.class);
      
        root = inflater.inflate(R.layout.fragment_predictions, container, false);

        root.findViewById(R.id.saveButton).setOnClickListener(this::saveButtonPressed);
        root.findViewById(R.id.resetButton).setOnClickListener(this::resetButtonPressed);

        recyclerView = root.findViewById(R.id.driverlist);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(mLayoutManager);

        predictionsViewModel.setCacheDir(getActivity().getCacheDir());
        predictionsViewModel.loadDrivers(this, recyclerView, getResources());

        predictionsViewModel.getSaveSuccessful().observe(this, succes -> {
            if(succes) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("INFO")
                        .setMessage("Save succesful")
                        .setPositiveButton("OK", null)
                        .setIcon(R.drawable.ic_check_black_24dp)
                        .show();

            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("ERROR")
                        .setMessage("Save failed, try again later")
                        .setPositiveButton("OK", null)
                        .setIcon(R.drawable.ic_error_black_24dp)
                        .show();
            }
        });

        predictionsViewModel.getPrediction().observe(getActivity(), predictions -> {
            JSONArray listData = predictionsViewModel.getDriverData().getValue();

            for(int i = 0; i < recyclerView.getChildCount(); i++) {
                View view = recyclerView.getChildAt(i);
                try {
                    JSONObject driver = listData.getJSONObject(i);
                    if(predictions.contains(driver.get("driverId"))) {
                        view.callOnClick();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            root.findViewById(R.id.saveButton).setVisibility(View.INVISIBLE);
            root.findViewById(R.id.resetButton).setVisibility(View.INVISIBLE);
        });

        return root;
    }

    public void saveButtonPressed(View view) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Confirm")
                .setMessage("Are you sure you want to save your prediction.\nNOTE: it can't be changed later")
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    root.findViewById(R.id.saveButton).setVisibility(View.INVISIBLE);
                    root.findViewById(R.id.resetButton).setVisibility(View.INVISIBLE);

                    predictionsViewModel.savePrediction(predictions);
                })
                .setNegativeButton("Cancel", null)
                .show();
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