package com.example.esii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
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




public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailTextField;
    private EditText passwordTextField;
    private Button loginButton;
    private CallbackManager callbackManager;
    private LoginButton loginButtonFace;

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

        loginButton.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();

        loginButtonFace.setPermissions(Arrays.asList("	user_photos, email, publish_video, openid, catalog_management, " +
                "pages_show_list, read_page_mailboxes, business_management, pages_messaging, instagram_basic, instagram_manage_insights, instagram_content_publish," +
                " instagram_manage_messages, pages_read_engagement, pages_manage_metadata, public_profile"));

        loginButtonFace.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult != null ){
                    GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                            if(jsonObject != null){
                                try {
                                    String profileId = jsonObject.getString("id");
                                    String username = jsonObject.getString("name");

                                    SharedPreferences p = getSharedPreferences("preferencias",MODE_PRIVATE);
                                    p.edit().putString("Id",profileId).apply();
                                    p.edit().putString("username",username).apply();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                    Bundle bundle = new Bundle();
                    bundle.putString("fields","	user_photos, email, publish_video, openid, catalog_management, " +
                            "pages_show_list, read_page_mailboxes, business_management, pages_messaging, instagram_basic, instagram_manage_insights, instagram_content_publish," +
                            " instagram_manage_messages, pages_read_engagement, pages_manage_metadata, public_profile");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(this, ChatActivity.class);
        startActivity(i);
    }
}