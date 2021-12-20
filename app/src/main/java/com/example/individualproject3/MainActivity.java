package com.example.individualproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MYPREF = "My_PREF_FILE_NAME";
    public static final String EMAIL = "EmailKey";

    Button registerButton;
    Button loginButton;

    private EditText emailEdit;
    private EditText passwordEdit;
    private RadioGroup role;

    private int x = 0;
    private int y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MYPREF,
                Context.MODE_PRIVATE);

        role = findViewById(R.id.RoleSelect);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        emailEdit = findViewById(R.id.EmailEdit);
        passwordEdit = findViewById(R.id.PasswordEdit);

        registerButton.setOnClickListener(this::registration);
        loginButton.setOnClickListener(this::login);
    }

    private void login(View view) {
        final String email = emailEdit.getText().toString().trim();
        final String password = passwordEdit.getText().toString().trim();
        x = 0;
        y = 0;

        if (email.isEmpty()){
            emailEdit.setError("Email Required");
            emailEdit.requestFocus();
        }
        if (password.isEmpty()) {
            passwordEdit.setError("Password required");
            passwordEdit.requestFocus();
            return;
        }
        int checkedId = role.getCheckedRadioButtonId();
        if (checkedId != R.id.parentRadio && checkedId != R.id.childRadio){
            Toast.makeText(getApplicationContext(), "Pick child or parent", Toast.LENGTH_LONG).show();
            return;
        }
        if (checkedId == R.id.parentRadio) {
            ParentAccount t = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .dataOrgDAO()
                    .findParentByEmail(email);
            if (t == null)
                x = 1;
        }
        else{
            ChildAccount t = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .dataOrgDAO()
                    .findChildByEmail(email);
            if (t == null)
                x = 1;
        }
        if (x == 1){
            Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_LONG).show();
            return;
        }
        if (checkedId == R.id.parentRadio) {
            ParentAccount t = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .dataOrgDAO()
                    .findParentByEmailPassword(email, password);
            if (t == null)
                y = 1;
        }
        else{
            ChildAccount t = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .dataOrgDAO()
                    .findChildByEmailPassword(email, password);
            if (t == null)
                y = 1;
        }
        if (y == 1){
            Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(EMAIL, email);
        editor.apply();
        Toast.makeText(getBaseContext(), "Logged in", Toast.LENGTH_LONG).show();
        if (checkedId == R.id.parentRadio) {
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
        }
        else {
            startActivity(new Intent(MainActivity.this, LevelSelect.class));
        }
    }

    private void registration(View view) {
        Intent register = new Intent(getBaseContext(), Register.class);
        startActivity(register);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(EMAIL, "");
        editor.apply();
    }
}