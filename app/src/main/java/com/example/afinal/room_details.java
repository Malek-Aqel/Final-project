package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class room_details extends AppCompatActivity {

    room myroom ;
    TextView area;
    TextView price;
    TextView ava;
    TextView desc;
    Button btnreserve;

    SliderView sliderView;
    int[] images = {R.drawable.detail1_1, R.drawable.detail1_2, R.drawable.detail1_3, R.drawable.detail1_4, R.drawable.detail1_5, R.drawable.detail1_6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        myroom = (room) getIntent().getSerializableExtra("room");

        //Toast.makeText(getApplicationContext(), myroom.name, Toast.LENGTH_SHORT).show();

        sliderView = findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        area = findViewById(R.id.area);
        price = findViewById(R.id.price);
        ava = findViewById(R.id.avalible);
        desc = findViewById(R.id.desc);
        btnreserve = findViewById(R.id.Reservation);

        area.setText("Area : "+myroom.getArea().toString());

        price.setText("Price Per Day : "+myroom.getPrice()+"$");
        desc.setText(myroom.getDisc().toString());

        if(myroom.getAvailable()==0) {
            ava.setText("Availablity : Available");
        }else{
            ava.setText("Available : Not Available");
            btnreserve.setClickable(false);
        }



    }

    public void reservation(View view) {
        Intent intent = new Intent(this,reservation.class);
        intent.putExtra("room", myroom);
        startActivity(intent);
    }
}