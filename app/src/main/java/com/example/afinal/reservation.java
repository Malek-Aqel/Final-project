package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class reservation extends AppCompatActivity {
    room myroom ;
    private EditText etname, etfrom , etto;
    private Button btnreserve;
    private String URL = "http://10.0.2.2/API/login/reserve.php";
    private String fullname;
    private Date dfrom;
    private Date dto;
    private int id ;

    String fromm , too;


    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private DatePickerDialog datePickerDialog2;
    private Button dateButton2;


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

        try {
            dfrom=new SimpleDateFormat("dd/MM/yyyy").parse(getTodaysDate());
            dto=new SimpleDateFormat("dd/MM/yyyy").parse(getTodaysDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        initDatePicker2();
        dateButton2 = findViewById(R.id.datePickerButton2);
        dateButton2.setText(getTodaysDate());


    }


    public void reserve(View view) {


        fullname = etname.getText().toString().trim();




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

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                fromm = date;

                try {
                    dfrom=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dateButton.setText(date);


            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

    }


    private void initDatePicker2()
    {
        DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);

                too = date;
                dateButton2.setText(date);


            }
        };

        Calendar cal1 = Calendar.getInstance();
        int year = cal1.get(Calendar.YEAR);
        int month = cal1.get(Calendar.MONTH);
        int day = cal1.get(Calendar.DAY_OF_MONTH);

        int style1 = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog2 = new DatePickerDialog(this, style1, dateSetListener2, year, month, day);
        //datePickerDialog2.getDatePicker().setMinDate(dfrom.getTime());


    }

    private String makeDateString(int day, int month, int year)
    {
        return  year + "-" + month + "-" + day;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";


        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void openDatePicker2(View view) {
        datePickerDialog2.show();
    }
}