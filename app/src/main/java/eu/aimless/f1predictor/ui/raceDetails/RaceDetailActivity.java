package eu.aimless.f1predictor.ui.raceDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import eu.aimless.f1predictor.R;

public class RaceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_detail);

        findBundle();
        defineInformation();
    }

    private void findBundle() {
        Bundle b = getIntent().getExtras();
        if(b != null) {
            int value = b.getInt("raceId");
            Log.d("Found this: ", ""+value+"");
        }
    }

    private void defineInformation() {
        defineRacename((TextView)findViewById(R.id.textViewRaceName));
        defineWinner((TextView)findViewById(R.id.textViewWinner));
        definePole((TextView)findViewById(R.id.textViewPoleposition));
        defineFastestlap((TextView)findViewById(R.id.textViewFastestlap));
    }

    private void defineRacename(TextView nameView) {
        nameView.setText("Monaco");
    }

    private void defineWinner(TextView winnerView) {
        winnerView.setText("Lewis Hamilton");
    }

    private void definePole(TextView poleView) {
        poleView.setText("Charles LeClerc");
    }

    private void defineFastestlap(TextView fastesView) {
        fastesView.setText("Max verstappen");
    }

}
