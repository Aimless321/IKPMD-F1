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
    private ImageView winnerLogo;
    private ImageView poleLogo;

    private TextView pos1;
    private TextView pos2;
    private TextView pos3;
    private TextView pos4;
    private TextView pos5;
    private TextView pos6;
    private TextView pos7;
    private TextView pos8;
    private TextView pos9;
    private TextView pos10;

    private TextView pos11;
    private TextView pos12;
    private TextView pos13;
    private TextView pos14;
    private TextView pos15;
    private TextView pos16;
    private TextView pos17;
    private TextView pos18;
    private TextView pos19;
    private TextView pos20;

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

        new FirestoreHelper().getWinner(raceid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    Map<String, Object> data = document.getData();
                    Map<String, Object> foundResult1 = ((List<Map<String, Object>>)data.get("Results")).get(0);
                    Map<String, Object> foundResult2 = ((List<Map<String, Object>>)data.get("Results")).get(1);
                    Map<String, Object> foundResult3 = ((List<Map<String, Object>>)data.get("Results")).get(2);
                    Map<String, Object> foundResult4 = ((List<Map<String, Object>>)data.get("Results")).get(3);
                    Map<String, Object> foundResult5 = ((List<Map<String, Object>>)data.get("Results")).get(4);
                    Map<String, Object> foundResult6 = ((List<Map<String, Object>>)data.get("Results")).get(5);
                    Map<String, Object> foundResult7 = ((List<Map<String, Object>>)data.get("Results")).get(6);
                    Map<String, Object> foundResult8 = ((List<Map<String, Object>>)data.get("Results")).get(7);
                    Map<String, Object> foundResult9 = ((List<Map<String, Object>>)data.get("Results")).get(8);
                    Map<String, Object> foundResult10 = ((List<Map<String, Object>>)data.get("Results")).get(9);
                    Map<String, Object> foundResult11 = ((List<Map<String, Object>>)data.get("Results")).get(10);
                    Map<String, Object> foundResult12 = ((List<Map<String, Object>>)data.get("Results")).get(11);
                    Map<String, Object> foundResult13 = ((List<Map<String, Object>>)data.get("Results")).get(12);
                    Map<String, Object> foundResult14 = ((List<Map<String, Object>>)data.get("Results")).get(13);
                    Map<String, Object> foundResult15 = ((List<Map<String, Object>>)data.get("Results")).get(14);
                    Map<String, Object> foundResult16 = ((List<Map<String, Object>>)data.get("Results")).get(15);
                    Map<String, Object> foundResult17 = ((List<Map<String, Object>>)data.get("Results")).get(16);
                    Map<String, Object> foundResult18 = ((List<Map<String, Object>>)data.get("Results")).get(17);
                    Map<String, Object> foundResult19 = ((List<Map<String, Object>>)data.get("Results")).get(18);
                    Map<String, Object> foundResult20 = ((List<Map<String, Object>>)data.get("Results")).get(19);

                    String namePos1 = ((Map<String, Object>)foundResult1.get("Driver")).get("familyName").toString();
                    pos1.setText(namePos1);
                    String namePos2 = ((Map<String, Object>)foundResult2.get("Driver")).get("familyName").toString();
                    pos2.setText(namePos2);
                    String namePos3 = ((Map<String, Object>)foundResult3.get("Driver")).get("familyName").toString();
                    pos3.setText(namePos3);
                    String namePos4 = ((Map<String, Object>)foundResult4.get("Driver")).get("familyName").toString();
                    pos4.setText(namePos4);
                    String namePos5 = ((Map<String, Object>)foundResult5.get("Driver")).get("familyName").toString();
                    pos5.setText(namePos5);
                    String namePos6 = ((Map<String, Object>)foundResult6.get("Driver")).get("familyName").toString();
                    pos6.setText(namePos6);
                    String namePos7 = ((Map<String, Object>)foundResult7.get("Driver")).get("familyName").toString();
                    pos7.setText(namePos7);
                    String namePos8 = ((Map<String, Object>)foundResult8.get("Driver")).get("familyName").toString();
                    pos8.setText(namePos8);
                    String namePos9 = ((Map<String, Object>)foundResult9.get("Driver")).get("familyName").toString();
                    pos9.setText(namePos9);
                    String namePos10 = ((Map<String, Object>)foundResult10.get("Driver")).get("familyName").toString();
                    pos10.setText(namePos10);

                    String namePos11 = ((Map<String, Object>)foundResult11.get("Driver")).get("familyName").toString();
                    pos11.setText(namePos11);
                    String namePos12 = ((Map<String, Object>)foundResult12.get("Driver")).get("familyName").toString();
                    pos12.setText(namePos12);
                    String namePos13 = ((Map<String, Object>)foundResult13.get("Driver")).get("familyName").toString();
                    pos13.setText(namePos13);
                    String namePos14 = ((Map<String, Object>)foundResult14.get("Driver")).get("familyName").toString();
                    pos14.setText(namePos14);
                    String namePos15 = ((Map<String, Object>)foundResult15.get("Driver")).get("familyName").toString();
                    pos15.setText(namePos15);
                    String namePos16 = ((Map<String, Object>)foundResult16.get("Driver")).get("familyName").toString();
                    pos16.setText(namePos16);
                    String namePos17 = ((Map<String, Object>)foundResult17.get("Driver")).get("familyName").toString();
                    pos17.setText(namePos17);
                    String namePos18 = ((Map<String, Object>)foundResult18.get("Driver")).get("familyName").toString();
                    pos18.setText(namePos18);
                    String namePos19 = ((Map<String, Object>)foundResult19.get("Driver")).get("familyName").toString();
                    pos19.setText(namePos19);
                    String namePos20 = ((Map<String, Object>)foundResult20.get("Driver")).get("familyName").toString();
                    pos20.setText(namePos20);

                }
            }
        });

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
        winnerLogo = (ImageView)findViewById(R.id.logoWinner);
        poleLogo = (ImageView)findViewById(R.id.logoPole);

        pos1 = (TextView)findViewById(R.id.textViewPos1);
        pos2 = (TextView)findViewById(R.id.textViewPos2);
        pos3 = (TextView)findViewById(R.id.textViewPos3);
        pos4 = (TextView)findViewById(R.id.textViewPos4);
        pos5 = (TextView)findViewById(R.id.textViewPos5);
        pos6 = (TextView)findViewById(R.id.textViewPos6);
        pos7 = (TextView)findViewById(R.id.textViewPos7);
        pos8 = (TextView)findViewById(R.id.textViewPos8);
        pos9 = (TextView)findViewById(R.id.textViewPos9);
        pos10 = (TextView)findViewById(R.id.textViewPos10);

        pos11 = (TextView)findViewById(R.id.textViewPos11);
        pos12 = (TextView)findViewById(R.id.textViewPos12);
        pos13 = (TextView)findViewById(R.id.textViewPos13);
        pos14 = (TextView)findViewById(R.id.textViewPos14);
        pos15 = (TextView)findViewById(R.id.textViewPos15);
        pos16 = (TextView)findViewById(R.id.textViewPos16);
        pos17 = (TextView)findViewById(R.id.textViewPos17);
        pos18 = (TextView)findViewById(R.id.textViewPos18);
        pos19 = (TextView)findViewById(R.id.textViewPos19);
        pos20 = (TextView)findViewById(R.id.textViewPos20);
    }


}
