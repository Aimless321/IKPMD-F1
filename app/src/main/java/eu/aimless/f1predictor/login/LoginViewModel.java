package eu.aimless.f1predictor.login;

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

public class LoginViewModel extends ViewModel {

    private final String TAG = "LoginViewModel";

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<Boolean> isLoggedIn;
    private MutableLiveData<String> error;

    public LoginViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        isLoggedIn = new MutableLiveData<>();
        isLoggedIn.setValue(false);
        error = new MutableLiveData<>();
    }

    /**
     * Check if a user is logged in already
     */
    public boolean checkLoggedIn() {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            return user.isEmailVerified();
        }

        return false;
    }

    public void validateLogin(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        //Sign-In success
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        if(user.isEmailVerified()) {
                            isLoggedIn.postValue(true);
                        } else {
                            error.postValue("Please verify your email");
                        }

                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        error.postValue("Authentication Failed");
                    }
                }
            });
    }

    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }


    public LiveData<String> getError() {
        return error;
    }
}
