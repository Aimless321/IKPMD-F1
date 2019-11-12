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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.ApiRepository;
import eu.aimless.f1predictor.repository.FirestoreHelper;

public class PredictionsViewModel extends ViewModel {

    private File cacheDir;
    private MutableLiveData<JSONArray> driverData = new MutableLiveData<>();
    private MutableLiveData<Boolean> saveSuccesful = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> prediction = new MutableLiveData<>();

    public void setCacheDir(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    public void loadDrivers(final View.OnClickListener listener, final RecyclerView recyclerView, final Resources resources) {
        FirestoreHelper firestoreHelper = new FirestoreHelper();
        firestoreHelper.getLatestRaceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }

            Double raceId = task.getResult().getDouble("id");


            firestoreHelper.getPrediction(raceId.intValue()+1).addOnCompleteListener(task1 -> {
                if(!task1.isSuccessful()) {
                   return;
                }

                DocumentSnapshot snapshot = task1.getResult();

                ApiRepository.getInstance(cacheDir).getDrivers(response -> {
                    try {
                        JSONArray listData = response.getJSONObject("MRData")
                                .getJSONObject("DriverTable")
                                .getJSONArray("Drivers");

                        driverData.postValue(listData);

                        recyclerView.setAdapter(
                                new DriverListAdapter(listener, listData, resources));

                        ArrayList<String> prediction = (ArrayList<String>) snapshot.get("prediction");
                        if(prediction != null) {
                            this.prediction.postValue(prediction);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            });
        });
    }

    public void savePrediction(ArrayList<String> predictions) {
        FirestoreHelper firestoreHelper = new FirestoreHelper();

        firestoreHelper.getLatestRaceId().addOnCompleteListener(task -> {
            if(!task.isSuccessful()) {
                return;
            }

            Double raceId = task.getResult().getDouble("id");

            firestoreHelper.savePrediction(raceId.intValue()+1, predictions).addOnCompleteListener(task1 -> {
                saveSuccesful.postValue(task1.isSuccessful());
            });
        });
    }

    public LiveData<Boolean> getSaveSuccessful() {
        return saveSuccesful;
    }

    public LiveData<JSONArray> getDriverData() {
        return driverData;
    }

    public LiveData<ArrayList<String>> getPrediction() {
        return prediction;
    }
}