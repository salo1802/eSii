package com.example.esii;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ListView messageslist;
    private ArrayList <Message> messages;
    private ArrayAdapter <Message> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageslist = findViewById(R.id.messagesList);
        messages = new ArrayList<>();
        adapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        messageslist.setAdapter(adapter);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newGraphPathRequest(
         accessToken,
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



    }

    private void loadMessages() {

    }


}
