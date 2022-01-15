package com.example.afinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class rooms extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter roomAdapter;
    RecyclerView.LayoutManager layoutmanager;

    int[] roomImages = {R.drawable.standredplus, R.drawable.delux,
            R.drawable.deluxplus, R.drawable.premium, R.drawable.premiumplus,
            R.drawable.standerd, R.drawable.suite, R.drawable.suiteplus};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        recyclerView = findViewById(R.id.rvmain);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);


        String roomsAPI = ("http://10.0.2.2/API/rooms.php");



        sendGetRequest();


    }

    public void Home(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private String getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            String json;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {



                try {
                    URL url = new URL(urlWebService);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    StringBuilder sb = new StringBuilder();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));



                    while ((json = bufferedReader.readLine()) != null) {

                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();

        return getJSON.json;
    }

    private String[] sendGetRequest() {

        String[] roomNameList1 = new String[8];
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="http://10.0.2.2/API/rooms.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    ArrayList<room> nn = new ArrayList<>();
                    ArrayList<String> nnb = new ArrayList<>();
                    String[] roomDescriptionList = new String[jsonArray.length()];

                    for(int i = 0 ; i < jsonArray.length() ; i++){

                        JSONObject jsonObj = (JSONObject) jsonArray.get(i);


                        int id = Integer.parseInt(jsonObj.get("id").toString());
                        String name = jsonObj.get("name").toString();
                        String disc = jsonObj.get("disc").toString();
                        int price = Integer.parseInt(jsonObj.get("price").toString());
                        int available = Integer.parseInt(jsonObj.get("available").toString());
                        Date from = new SimpleDateFormat("yyyy-MM-DD").parse(jsonObj.get("fromm").toString());
                        Date too = new SimpleDateFormat("yyyy-MM-DD").parse(jsonObj.get("too").toString());
                        String area = jsonObj.get("area").toString();




                        nnb.add(name);


                        room r = new room( id,  name,  disc,  price,  available,  from, too,  area);

                        nn.add(r);

                    }


                    for(int i = 0 ; i < nn.size() ; i++){

                        roomNameList1[i] = nnb.get(i);
                    }

                    Toast.makeText(getApplicationContext(), roomNameList1[0], Toast.LENGTH_SHORT).show();



                    roomAdapter = new roomAdapter(getApplicationContext(), roomImages , nn);
                    recyclerView.setAdapter(roomAdapter);




                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(stringRequest);


        return roomNameList1;
    }



}