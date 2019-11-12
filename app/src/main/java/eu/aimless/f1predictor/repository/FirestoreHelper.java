package eu.aimless.f1predictor.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreHelper {

    private FirebaseFirestore db;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<DocumentSnapshot> getLatestRaceId() { return db.document("/results/lastAdded").get(); }

    public Task<QuerySnapshot> getRaceResults() {
        return db.collection("results/race/2019").get();
    }

    public Task<QuerySnapshot> getLatestRace() {
        return db.collection("results/race/2019/")
                .orderBy("round", Query.Direction.DESCENDING)
                .limit(1).get();
    }

    public Task<Void> savePrediction(int raceId, ArrayList<String> prediction) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> data = new HashMap<>();
        data.put("prediction", prediction);


        return db.document("/users/predictions/" + uid + "/" + raceId)
                .set(data);
    }

    public Task<DocumentSnapshot> getPrediction(int raceId) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return db.document("/users/predictions/" + uid + "/" + raceId)
                .get();
    }
}
