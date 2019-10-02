package eu.aimless.f1predictor.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;

public class Firestore {

    private FirebaseFirestore db;

    public Firestore() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<QuerySnapshot> getRaceResults() {
        return db.collection("results/race/2019").get();
    }

    public Task<QuerySnapshot> getLatestRace() {
        return db.collection("results/race/2019/")
                .orderBy("round", Query.Direction.DESCENDING)
                .limit(1).get();
    }
}
