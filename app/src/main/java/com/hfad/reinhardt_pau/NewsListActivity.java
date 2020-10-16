package com.hfad.reinhardt_pau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class NewsListActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ArrayList<String> array;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        list = (ListView) findViewById(R.id.list);
        array = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        list.setAdapter(adapter);



        String keyword = getIntent().getStringExtra("keyword");
        getJson(keyword);
    }


    public void getJson(String keyword) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://newsapi.org/v2/everything?q="+keyword+"&apiKey=83fd76d01d554848a659ed5159b4947d";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        addToList(response);
//                        textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        textView.setText(error.toString());
                    }
                });

        queue.add(jsonObjectRequest);

    }

    public void addToList(JSONObject jsonObject) {



        try {
            final JSONArray jsonArray = jsonObject.getJSONArray("articles");

            for (int i = 0; i < jsonArray.length(); i++) {
                adapter.add(jsonArray.getJSONObject(i).getString("title"));
            }

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    try {
                        intent.putExtra("article", jsonArray.getJSONObject(i).toString());
                    } catch (JSONException e) {}
                    startActivity(intent);
                }
            });

        } catch (JSONException e) {

        }

    }
}