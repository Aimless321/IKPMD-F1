package eu.aimless.f1predictor.ui.races;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.model.Race;

public class RaceListAdapter extends ArrayAdapter<Race> {

    public RaceListAdapter(Context context, int resource, List<Race> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if(convertView == null) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());

            convertView = li.inflate(R.layout.racelist_row, parent, false);
            vh.raceName = (TextView) convertView.findViewById(R.id.raceName);
            vh.round = (TextView) convertView.findViewById(R.id.round);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }
        Race race = getItem(position);
        vh.raceName.setText(race.getRaceName());
        vh.round.setText(String.valueOf(race.getRound())); //int moet String worden
        return convertView;
    }

    private static class ViewHolder {
        TextView raceName;
        TextView round;
    }
}
