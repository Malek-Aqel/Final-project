package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class reservation extends AppCompatActivity {
    room myroom ;
    private EditText etname, etfrom , etto;
    private Button btnreserve;
    private String URL = "http://10.0.2.2/API/login/reserve.php";
    private String fullname;
    private Date dfrom , dto;
    private int id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        myroom = (room) getIntent().getSerializableExtra("room");

        etname = findViewById(R.id.etname);
        etfrom = findViewById(R.id.etfrom);
        etto = findViewById(R.id.etto);
        btnreserve = findViewById(R.id.reserve);
        id = myroom.getId();
    }


    public void reserve(View view) {

        String fromm , too;
        fullname = etname.getText().toString().trim();
        fromm = etfrom.getText().toString().trim();
        too = etto.getText().toString().trim();


        
        try {
            dfrom=new SimpleDateFormat("dd/MM/yyyy").parse(fromm);
            dto=new SimpleDateFormat("dd/MM/yyyy").parse(too);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(!fullname.equals("") && !etfrom.equals("") && !etto.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(getApplicationContext(), "you reserved the room.", Toast.LENGTH_SHORT).show();
                        btnreserve.setClickable(false);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    } else if (response.equals("failure")) {
                        Toast.makeText(getApplicationContext(), "something wrong!!", Toast.LENGTH_SHORT).show();            }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("id", ""+id);
                    data.put("fromm", fromm);
                    data.put("too", too);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }


    }
}