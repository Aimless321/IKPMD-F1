package eu.aimless.f1predictor.repository;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import java.io.File;

public class ApiRepository {

    private static ApiRepository instance;

    public static ApiRepository getInstance(File cacheDir) {
        if (instance == null) {
            instance = new ApiRepository(cacheDir);
        }

        return instance;
    }

    private RequestQueue requestQueue;

    private ApiRepository(File cacheDir) {
        Cache cache = new DiskBasedCache(cacheDir, 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }


    public void getDrivers(Response.Listener<JSONObject> listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://ergast.com/api/f1/current/last/drivers.json",
                null, listener, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ApiRepo", error.getMessage());
                }
        });

        requestQueue.add(jsonObjectRequest);
    }

}
