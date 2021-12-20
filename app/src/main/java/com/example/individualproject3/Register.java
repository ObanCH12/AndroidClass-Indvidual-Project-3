package com.example.individualproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private Button registerBtn;
    private Button parentBtn;
    private Button childBtn;

    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText parentEmailEdit;
    private EditText nameEdit;

    private int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        parentBtn = findViewById(R.id.parentButton);
        childBtn = findViewById(R.id.childButton);
        registerBtn = findViewById(R.id.registrationButton);

        parentEmailEdit = findViewById(R.id.parentEmailEdit);
        nameEdit = findViewById(R.id.NameEdit);
        emailEdit = findViewById(R.id.EmailEdit);
        passwordEdit = findViewById(R.id.PasswordEdit);


        parentBtn.setOnClickListener(this);
        childBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this::register);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.parentButton: {
                nameEdit.setVisibility(View.VISIBLE);
                emailEdit.setVisibility(View.VISIBLE);
                passwordEdit.setVisibility(View.VISIBLE);
                registerBtn.setVisibility(View.VISIBLE);
                parentBtn.setVisibility(View.GONE);
                childBtn.setVisibility(View.GONE);
                break;
            }
            case R.id.childButton:{
                parentEmailEdit.setVisibility(View.VISIBLE);
                nameEdit.setVisibility(View.VISIBLE);
                emailEdit.setVisibility(View.VISIBLE);
                passwordEdit.setVisibility(View.VISIBLE);
                registerBtn.setVisibility(View.VISIBLE);
                parentBtn.setVisibility(View.GONE);
                childBtn.setVisibility(View.GONE);
                break;
            }
        }
    }
    public boolean isValid(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private void register(View view) {

            final String name = nameEdit.getText().toString().trim();
            final String email = emailEdit.getText().toString().trim();
            final String password = passwordEdit.getText().toString().trim();
            final String parentEmail = parentEmailEdit.getText().toString().trim();
            n = 0;

            if (name.isEmpty()) {
                nameEdit.setError("Name required");
                nameEdit.requestFocus();
                return;
            }

            if (email.isEmpty()){
                emailEdit.setError("Email Required");
                emailEdit.requestFocus();
            }
            if (!isValid(email)){
                Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
                return;
            }
            if (password.isEmpty()) {
                passwordEdit.setError("Password required");
                passwordEdit.requestFocus();
                return;
            }
            if (parentEmailEdit.getVisibility() != View.GONE){
                if (parentEmail.isEmpty()){
                    parentEmailEdit.setError("Parent email required");
                    parentEmailEdit.requestFocus();
                    return;
                }
                if (!isValid(parentEmail)){
                    Toast.makeText(getApplicationContext(), "Invalid parent email", Toast.LENGTH_LONG).show();
                    return;
                }

                ParentAccount t = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .dataOrgDAO()
                        .findParentByEmail(parentEmail);
                if (t == null)
                    n = 1;
                if (n == 1){
                    parentEmailEdit.setError("Parent doesn't exist");
                    parentEmailEdit.requestFocus();
                    return;
                }
            }

            class registerUser extends AsyncTask<Void, Void, Integer> {

                @Override
                protected Integer doInBackground(Void... voids) {


                    if (parentEmailEdit.getVisibility() == View.GONE) {
                        ParentAccount note = new ParentAccount();
                        note.setParentName(name);
                        note.setParentEmail(email);
                        note.setParentPassword(password);
                        ParentAccount t =   DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .dataOrgDAO()
                                .findParentByEmail(email);
                        if (t != null) {
                            return 1;
                        }
                        //adding to database
                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .dataOrgDAO()
                                .insertParent(note);
                    }
                    else{
                        ChildAccount g = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .dataOrgDAO()
                                .findChildByEmail(email);
                        if (g != null){
                            return 1;
                        }


                        ParentAccount t = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .dataOrgDAO()
                                .findParentByEmail(parentEmail);

                        ChildAccount note = new ChildAccount();
                        note.setParentId(t.getParentId());
                        note.setChildName(name);
                        note.setChildEmail(email);
                        note.setChildPassword(password);
                        note.setLevel1(0);
                        note.setLevel2(0);
                        note.setLevel3(0);
                        note.setLevel4(0);
                        note.setLevel5(0);
                        note.setLevel6(0);
                        note.setLevelsCompleted(0);


                        //adding to database
                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .dataOrgDAO()
                                .insertChild(note);
                    }
                    return 0;
                }

                @Override
                protected void onPostExecute(Integer aVoid) {
                    super.onPostExecute(aVoid);
                    if (aVoid == 0) {
                        startActivity(new Intent(Register.this, MainActivity.class));
                        Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                }
            }

            registerUser st = new registerUser();
            st.execute();
        }
}