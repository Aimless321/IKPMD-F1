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

public class PredictionsViewModel extends ViewModel {

    private File cacheDir;
    private MutableLiveData<JSONArray> driverData = new MutableLiveData<>();

    public void setCacheDir(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    public void loadDrivers(final View.OnClickListener listener, final RecyclerView recyclerView, final Resources resources) {
        ApiRepository.getInstance(cacheDir).getDrivers(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray listData = response.getJSONObject("MRData")
                            .getJSONObject("DriverTable")
                            .getJSONArray("Drivers");

                    driverData.postValue(listData);

                    recyclerView.setAdapter(
                            new DriverListAdapter(listener, listData, resources));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LiveData<JSONArray> getDriverData() {
        return driverData;
    }
}