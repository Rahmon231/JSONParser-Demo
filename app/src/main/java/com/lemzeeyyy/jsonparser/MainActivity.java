package com.lemzeeyyy.jsonparser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<String> personNames = new ArrayList<>();
    ArrayList<String> emailIds = new ArrayList<>();
    ArrayList<String> mobileNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            JSONArray array = json.getJSONArray("users");
            for (int i = 0; i < array.length(); i++) {
                JSONObject userDetails = array.getJSONObject(i);
                personNames.add(userDetails.getString("name"));
                emailIds.add(userDetails.getString("email"));
                JSONObject mobileObj = userDetails.getJSONObject("contact");
                mobileNumbers.add(mobileObj.getString("mobile"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomAdapter adapter = new CustomAdapter(personNames,emailIds,mobileNumbers,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
    private String loadJSONFromAsset(){
        String json = null;
        try {
            InputStream is = getAssets().open("users_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }
}