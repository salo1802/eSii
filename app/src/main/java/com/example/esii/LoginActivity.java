package com.example.esii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;




public class LoginActivity extends AppCompatActivity {

    private EditText emailTextField;
    private EditText passwordTextField;
    private Button loginButton;
    private CallbackManager callbackManager;
    private LoginButton loginButtonFace;
    private  String profileId;
    private String username;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        emailTextField= findViewById(R.id.emailTextField);
        passwordTextField= findViewById(R.id.passwordTextField);
        loginButton= findViewById(R.id.loginButton);
        loginButtonFace = findViewById(R.id.login_button);;

        auth= FirebaseAuth.getInstance();



        callbackManager = CallbackManager.Factory.create();

        //loginButtonFace.setPermissions(Arrays.asList("id,name,email,gender,birthday"));

        loginButtonFace.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult != null ){
                    GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                            if(jsonObject != null){
                                try {
                                    profileId = jsonObject.getString("id");
                                    username = jsonObject.getString("name");

                                    SharedPreferences p = getSharedPreferences("preferencias",MODE_PRIVATE);
                                    p.edit().putString("Id",profileId).apply();
                                    p.edit().putString("username",username).apply();
                                    Log.e("Prueba",profileId);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                    Bundle bundle = new Bundle();
                    bundle.putString("fields","id,name,email,gender,birthday");
                    graphRequest.setParameters(bundle);
                    graphRequest.executeAsync();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}