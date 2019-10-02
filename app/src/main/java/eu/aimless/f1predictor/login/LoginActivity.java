package eu.aimless.f1predictor.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import eu.aimless.f1predictor.MainActivity;
import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    private LoginViewModel loginViewModel;
    private EditText emailBox;
    private EditText passwordBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getIsLoggedIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if(value) {
                    loadMainActivity();
                }
            }
        });

        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                boxError(emailBox);
                boxError(passwordBox);
            }
        });

        setContentView(R.layout.activity_login);

        emailBox = findViewById(R.id.userName);
        passwordBox = findViewById(R.id.password);
    }

    public void onLoginButtonPressed(View view) {
        resetInputBoxes();

        String email = emailBox.getText().toString();
        String password = passwordBox.getText().toString();

        //Check if the values are not empty
        if(email.equals("")) {
            boxError(emailBox);
            return;
        }

        if(password.equals("")) {
            boxError(passwordBox);
            return;
        }

        loginViewModel.validateLogin(email, password);
    }

    public void onRegisterButtonPressed(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void resetInputBoxes() {
        //Reset background
        emailBox.setBackgroundResource(R.drawable.background_textbox);
        passwordBox.setBackgroundResource(R.drawable.background_textbox);
    }

    private void boxError(EditText box) {
        box.setBackgroundResource(R.drawable.background_textbox_error);
    }

    private void loadMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
