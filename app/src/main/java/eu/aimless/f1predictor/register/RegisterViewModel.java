package eu.aimless.f1predictor.register;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterViewModel extends ViewModel {

    private final String TAG = "RegisterViewModel";

    private FirebaseAuth firebaseAuth;

    private MutableLiveData<Boolean> isRegistered;
    private MutableLiveData<String> error;
    private MutableLiveData<String> mailStatus;

    public RegisterViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();

        isRegistered = new MutableLiveData<>();
        isRegistered.setValue(false);
        error = new MutableLiveData<>();
    }

    public void registerButtonPressed(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();

                        if(!user.isEmailVerified()) {
                            user.sendEmailVerification();
                        }

                        isRegistered.postValue(true);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        error.postValue("Authentication Failed");
                    }
                }
            });
    }

    public LiveData<Boolean> getIsRegistered() {
        return isRegistered;
    }

    public LiveData<String> getError() {
        return error;
    }
}
