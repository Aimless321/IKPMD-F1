package eu.aimless.f1predictor.repository.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import eu.aimless.f1predictor.repository.model.Race;

public class FirebaseHelper {

    private FirebaseFirestore db;

    public FirebaseHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public Race getLatestRace() {
        String docPath = "race/" + getLatestRaceDocPath();
        Log.d("Firebase", "Getting race: "+docPath);

        Task<DocumentSnapshot> task = db.collection("results").document(docPath).get();

        try {
            DocumentSnapshot documentSnapshot = Tasks.await(task, 250, TimeUnit.MILLISECONDS);
            Race race = new Race();
            race.setRaceName((String)documentSnapshot.get("raceName"));

            return race;
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            return new Race();
        }
    }

    private String getLatestRaceDocPath() {
        Task<DocumentSnapshot> task = db.collection("results").document("lastAdded").get();
        try {
            DocumentSnapshot documentSnapshot = Tasks.await(task, 250, TimeUnit.MILLISECONDS);
            return documentSnapshot.get("year") + "/" + documentSnapshot.get("id");
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
