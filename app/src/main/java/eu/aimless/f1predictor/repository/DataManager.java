package eu.aimless.f1predictor.repository;

import eu.aimless.f1predictor.repository.firebase.FirebaseHelper;

public class DataManager {
    //Singleton Pattern
    private static DataManager instance;

    public static DataManager getInstance() {
        if(instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    private FirebaseHelper firebaseHelper;

    private DataManager(){
        firebaseHelper = new FirebaseHelper();
    }

    public String getLatestRaceName() {
        return firebaseHelper.getLatestRace().getRaceName();
    }
}
