package eu.aimless.f1predictor.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import eu.aimless.f1predictor.MainActivity;
import eu.aimless.f1predictor.R;
import eu.aimless.f1predictor.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    private EditText email;
    private EditText password;
    private EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        registerViewModel.getIsRegistered().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if(value) {
                    loadLoginActivity();
                }
            }
        });

        registerViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                boxError(email);
                boxError(password);
            }
        });

        setContentView(R.layout.activity_register);

        email = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        password.setClickable(false);

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
                    boxError(confirmPassword);
                } else {
                    confirmPassword.setBackgroundResource(R.drawable.background_textbox);
                    password.setClickable(true);
                }
            }
        });
    }

    public void onRegisterButtonPressed(View view) {
        resetInputBoxes();

        String sEmail = email.getText().toString();
        String password = confirmPassword.getText().toString();

        registerViewModel.registerButtonPressed(sEmail, password);
    }

    private void resetInputBoxes() {
        //Reset background
        email.setBackgroundResource(R.drawable.background_textbox);
        password.setBackgroundResource(R.drawable.background_textbox);
        confirmPassword.setBackgroundResource(R.drawable.background_textbox);
    }

    private void boxError(EditText box) {
        box.setBackgroundResource(R.drawable.background_textbox_error);
    }

    private void loadLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("email", email.getText().toString());
        bundle.putString("password", password.getText().toString());

        intent.putExtras(bundle);

        startActivity(intent);
    }
}
