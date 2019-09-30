package eu.aimless.f1predictor.ui.notifications;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import eu.aimless.f1predictor.repository.Firestore;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();

        new Firestore().getLatestRace().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot documents = task.getResult();
                    String raceName = documents.getDocuments().get(0).getString("raceName");

                    mText.setValue(raceName);
                }

            }
        });
    }

    public LiveData<String> getText() {
        return mText;
    }
}