package com.example.afinal;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText etName, etusername , etphone ,etEmail, etPassword, etReenterPassword;
    private TextView tvStatus;
    private Button btnRegister;
    private String URL = "http://10.0.2.2/API/login/register.php";
    private String fullname, username , phoneno ,email, password, reenterPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.etName);
        etusername = findViewById(R.id.etusername);
        etphone = findViewById(R.id.etphoneno);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etReenterPassword = findViewById(R.id.etReenterPassword);
        tvStatus = findViewById(R.id.tvStatus);
        btnRegister = findViewById(R.id.btnRegister);
        fullname = email = username = phoneno = password = reenterPassword = "";
    }

    public void save(View view) {
        fullname = etName.getText().toString().trim();
        username = etusername.getText().toString().trim();
        phoneno = etphone.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        reenterPassword = etReenterPassword.getText().toString().trim();
        if(!password.equals(reenterPassword)){
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
        }
        else if(!fullname.equals("") && !email.equals("") && !password.equals("") && !username.equals("") && !phoneno.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        tvStatus.setText("Successfully registered.");
                        btnRegister.setClickable(false);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else if (response.equals("failure")) {
                        tvStatus.setText("Something went wrong!");                    }
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
                    data.put("fullname", fullname);
                    data.put("username", username);
                    data.put("email", email);
                    data.put("phone", phoneno);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
