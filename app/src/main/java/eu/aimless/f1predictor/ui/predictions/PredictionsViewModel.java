package eu.aimless.f1predictor.ui.predictions;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.ApiRepository;
import eu.aimless.f1predictor.repository.FirestoreHelper;

public class PredictionsViewModel extends ViewModel implements View.OnClickListener {

    private File cacheDir;
    private RecyclerView recyclerView;
    private JSONArray listData;
    private Resources resources;

    //Save prediction
    private int currentPrediction = 0;
    private ArrayList<String> predictions = new ArrayList<>();

    public void setCacheDir(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    public void loadDrivers(final RecyclerView recyclerView, final Resources resources) {
        this.recyclerView = recyclerView;
        this.resources = resources;

        ApiRepository.getInstance(cacheDir).getDrivers(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listData = response.getJSONObject("MRData")
                            .getJSONObject("DriverTable")
                            .getJSONArray("Drivers");

                    recyclerView.setAdapter(
                            new DriverListAdapter(PredictionsViewModel.this, listData, resources));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(currentPrediction >= 10) {
            return;
        }

        int itemPosition = recyclerView.getChildLayoutPosition(view);

        try {
            JSONObject driver = listData.getJSONObject(itemPosition);
            Log.d("Prediction", currentPrediction + ": " + driver.getString("driverId"));

            view.setVisibility(View.INVISIBLE);

            predictions.add(driver.getString("driverId"));
            currentPrediction++;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}