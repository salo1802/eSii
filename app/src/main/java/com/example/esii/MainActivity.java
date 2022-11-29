package com.example.esii;

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

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    ProfilePictureView profile_iv;
    TextView name_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
        profile_iv = findViewById(R.id.profile_iv);
        name_tv = findViewById(R.id.name);

        callbackManager = CallbackManager.Factory.create();

        loginButton.setPermissions(Arrays.asList("	user_photos, email, publish_video, openid, catalog_management, " +
                "pages_show_list, read_page_mailboxes, business_management, pages_messaging, instagram_basic, instagram_manage_insights, instagram_content_publish," +
                " instagram_manage_messages, pages_read_engagement, pages_manage_metadata, public_profile, read_mailbox"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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

                                    profile_iv.setPresetSize(ProfilePictureView.NORMAL);
                                    profile_iv.setProfileId(profileId);
                                    name_tv.setText(username);


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




}