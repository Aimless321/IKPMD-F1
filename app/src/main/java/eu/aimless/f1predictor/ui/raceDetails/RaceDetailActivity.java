package eu.aimless.f1predictor.ui.raceDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.utils.ObjectPool;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.repository.FirestoreHelper;

public class RaceDetailActivity extends AppCompatActivity {

    private TextView raceNameText;
    private TextView winnerText;
    private TextView poleText;
    private TextView fastestText;
    private ImageView winnerLogo;
    private ImageView poleLogo;
    private ImageView fastestLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_detail);



        final int raceid = findRaceid();
        defineInformation();

        new FirestoreHelper().getRace(raceid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    String raceName = document.getString("raceName");
                    Log.d("The Value is:", ""+raceName+""); //Controle of er een waarde is gevonden

                    raceNameText.setText(raceName);
                }
                else {
                    raceNameText.setText("Not found"); //Controle of de task succesful is
                }
            }
        });

        new FirestoreHelper().getPolePosition(raceid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    Map<String, Object> data = document.getData();
                    Map<String, Object> firstResult = ((List<Map<String, Object>>)data.get("QualifyingResults")).get(0);

                    String poleName = ((Map<String, Object>)firstResult.get("Driver")).get("familyName").toString();
                    String poleId = ((Map<String, Object>)firstResult.get("Driver")).get("driverId").toString();

                    poleText.setText(poleName);
                    putLogo(poleLogo, poleId);
                }
            }
        });

        new FirestoreHelper().getWinner(raceid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    Map<String, Object> data = document.getData();
                    Map<String, Object> firstResult = ((List<Map<String, Object>>)data.get("Results")).get(0);

                    String winnerName = ((Map<String, Object>)firstResult.get("Driver")).get("familyName").toString();
                    String winnerId = ((Map<String, Object>)firstResult.get("Driver")).get("driverId").toString();

                    winnerText.setText(winnerName);
                    putLogo(winnerLogo, winnerId);
                }
            }
        });

        putLogo(fastestLogo, "unknown");

    }

    private void putLogo(ImageView logo, String driver) {
        switch (driver) {
            case "leclerc":
            case "vettel":
                logo.setImageResource(R.drawable.ferrari128);
                break;
            case "hamilton":
            case "bottas":
                logo.setImageResource(R.drawable.mercedes128);
                break;
            case "max_verstappen":
            case "albon":
                logo.setImageResource(R.drawable.redbull128);
                break;
            case "ricciardo":
            case "rulkenberg":
                logo.setImageResource(R.drawable.renault128);
                break;
            case "kevin_magnussen":
            case "grosjean":
                logo.setImageResource(R.drawable.haas128);
                break;
            case "sainz":
            case "norris":
                logo.setImageResource(R.drawable.mclaren128);
                break;
            case "kvyat":
            case "gasly":
                logo.setImageResource(R.drawable.torrorosso128);
                break;
            case "stroll":
            case "perez":
                logo.setImageResource(R.drawable.racingpoint128);
                break;
            case "raikonnen":
            case "giovinazzi":
                logo.setImageResource(R.drawable.alfaromeo128);
                break;
            case "russel":
            case "kubica":
                logo.setImageResource(R.drawable.williams128);
                break;
            default:
                logo.setImageResource(R.drawable.formulaone128);
        }
    }

    private int findRaceid() {
        Bundle b = getIntent().getExtras();
        if(b != null) {
            int value = b.getInt("raceId");
            Log.d("Found this: ", ""+value+""); //Controle of er een waarde is gevonden

            return value;
        }
        else {
            return 0;
        }
    }

    private void defineInformation() {
        raceNameText = (TextView)findViewById(R.id.textViewRaceName);
        winnerText =(TextView)findViewById(R.id.textViewWinner);
        poleText = (TextView)findViewById(R.id.textViewPoleposition);
        fastestText = (TextView)findViewById(R.id.textViewFastestlap);
        winnerLogo = (ImageView)findViewById(R.id.logoWinner);
        poleLogo = (ImageView)findViewById(R.id.logoPole);
        fastestLogo = (ImageView)findViewById(R.id.logoFastestlap);
    }


}
