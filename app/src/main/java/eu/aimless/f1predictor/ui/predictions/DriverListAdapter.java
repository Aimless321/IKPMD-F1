package eu.aimless.f1predictor.ui.predictions;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.aimless.f1predictor.R;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.MyViewHolder> {
    private PredictionsViewModel mViewModel;
    private JSONArray mDrivers;
    private Resources mResources;

     public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public MyViewHolder(CardView v) {
            super(v);
            imageView = v.findViewById(R.id.driverImage);
            textView = v.findViewById(R.id.driverName);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DriverListAdapter(PredictionsViewModel viewModel, JSONArray drivers, Resources resources) {
         mViewModel = viewModel;
         mDrivers = drivers;
         mResources = resources;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DriverListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prediction_gridtile, parent, false);
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        marginParams.setMargins(5, 5, 5, 5);

        v.setOnClickListener(mViewModel);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
            JSONObject driver = mDrivers.getJSONObject(position);

            String name = driver.getString("familyName");
            String id = driver.getString("driverId");
            holder.textView.setText(name);
            holder.imageView.setImageResource(mResources.getIdentifier(id, "drawable",
                    "eu.aimless.f1predictor"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDrivers.length();
    }
}


