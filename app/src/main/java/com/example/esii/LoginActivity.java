package com.example.esii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;




public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailTextField;
    private EditText passwordTextField;
    private Button loginButton;
    private Button loginButtonFacebook;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        emailTextField= findViewById(R.id.emailTextField);
        passwordTextField= findViewById(R.id.passwordTextField);
        loginButton= findViewById(R.id.loginButton);
        loginButtonFacebook = findViewById(R.id.loginButtonFacebook);

        auth= FirebaseAuth.getInstance();

        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                if(!emailTextField.getText().toString().isEmpty() && !passwordTextField.getText().toString().isEmpty()){
                    auth.signInWithEmailAndPassword(emailTextField.getText().toString(), passwordTextField.getText().toString()).addOnCompleteListener(
                            task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(this, "Login exitoso", Toast.LENGTH_LONG).show();
                                   /* Intent i=new Intent (this, TeamSelectActivity.class);
                                    startActivity(i);
                                    finish();*/
                                }else{
                                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                }else{
                    Toast.makeText(this, "Por favor llenar todos los campos", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}