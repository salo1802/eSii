package com.example.esii;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ChatActivity extends AppCompatActivity {

    private ListView messageslist;
    private ArrayList <Message> messages;
    private ArrayAdapter <Message> adapter;
    private Collection <String> permisos;
    private String permisosStr = "user_photos, email, publish_video, openid, catalog_management, pages_show_list, read_page_mailboxes, business_management, pages_messaging, instagram_basic, instagram_manage_insights, instagram_content_publish, instagram_manage_messages, pages_read_engagement, pages_manage_metadata, public_profile";

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        permisos = Arrays.asList(permisosStr.split(","));

        messageslist = findViewById(R.id.messagesList);
        messages = new ArrayList<>();
        adapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        messageslist.setAdapter(adapter);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        AccessToken pageAT = new AccessToken("EAAMIL4gMUOoBAIbPTglXiPQ0pegcZAAMSxSxWoDaW1U27QkrhV12pbyo8cgwmtSn0PDoU3M8Hq4xgVR4XaD7XI2drhZAi8dBZC6Dv6d3zzDlEN7y0sDLpeidp1RIgBySyjHYZBveXGV5xJxAUa6jGuoi8zIJOS2tfliNIDkZCqDZBrrqlwQQTM",
                "853425169125610","5667113656682262", permisos, null, null, AccessTokenSource.WEB_VIEW, null,null,null);
        GraphRequest request = GraphRequest.newGraphPathRequest(
         pageAT,
                "/me/conversations",
               new GraphRequest.Callback() {
                    @Override
                    public void onCompleted( @Nullable GraphResponse graphResponse) {
                       JSONArray jsonArray = graphResponse.getJSONArray();
                        if (jsonArray != null) {
                            try {
                                for(int i = 0;i < jsonArray.length()-1; i++ ){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String from = jsonObject.getString("from");
                                    String time = jsonObject.getString("created_time");
                                    String message = jsonObject.getString("message");
                                    Message actual = new Message(from,message,time);


                                }

                                adapter.notifyDataSetChanged();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                });

        Bundle instaMetadata = new Bundle();
        Bundle messengerMetadata = new Bundle();
        instaMetadata.putString("fields", "messages{from,message,created_time}");
        messengerMetadata.putString("fields", "messages{from,message,created_time}");
        messengerMetadata.putString("platform", "messenger");
        instaMetadata.putString("platform", "instagram");
        request.setParameters(instaMetadata);
        request.executeAsync();
        request.setParameters(messengerMetadata);
        request.executeAsync();

        bottomNavigationView = findViewById(R.id.bottom_navbar);
        bottomNavigationView.setSelectedItemId(R.id.chat);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.chat:

                        return true;

                    case R.id.cont:
                        /*startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                        overridePendingTransition(0,0);*/
                        return true;

                    case R.id.courses:
                        startActivity(new Intent(getApplicationContext(), CoursesActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private void loadMessages() {

    }


}
