package eu.aimless.f1predictor.ui.raceDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.FirestoreHelper;

public class RaceDetailActivity extends AppCompatActivity {

    private TextView raceNameText;
    private TextView winnerText;
    private TextView poleText;
    private TextView fastestText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_detail);

        final int raceid = findRaceid();

        new FirestoreHelper().getRace(raceid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    String racename = document.getString("raceName");
                    raceNameText.setText(racename);
                }
            }
        });

        defineInformation();
    }

    private int findRaceid() {
        Bundle b = getIntent().getExtras();
        if(b != null) {
            int value = b.getInt("raceId");
            Log.d("Found this: ", ""+value+"");

            return value;
        }
        return 0;
    }

    private void defineInformation() {
        raceNameText = (TextView)findViewById(R.id.textViewRaceName);
        winnerText =(TextView)findViewById(R.id.textViewWinner);
        poleText = (TextView)findViewById(R.id.textViewPoleposition);
        fastestText = (TextView)findViewById(R.id.textViewFastestlap);
    }


}
