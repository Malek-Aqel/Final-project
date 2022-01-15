package com.example.afinal;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class roomAdapter extends RecyclerView.Adapter<roomAdapter.ViewHolder> {
    Context context;
    ArrayList<room> rooms = new ArrayList<>();
    int[] images;

    int pos;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName;
        TextView rowarea;
        TextView rowprice;
        TextView rowid;
        TextView ava;
        ImageView rowImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.roomname);
            rowarea = itemView.findViewById(R.id.area);
            rowprice = itemView.findViewById(R.id.price);
            rowid = itemView.findViewById(R.id.idd);
            rowImage = itemView.findViewById(R.id.imageView);
            ava = itemView.findViewById(R.id.avalibeity);
        }
    }
    public roomAdapter(Context context, int[] images , ArrayList<room> rooms){
        this.context = context;
        this.images = images;
        this.rooms = rooms;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                TextView roomno =(TextView) v.findViewById(R.id.idd);


                 pos = Integer.parseInt(roomno.getText().toString())-1;


                //Toast.makeText(context, ""+pos, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context.getApplicationContext(),room_details.class);
                intent.putExtra("room", rooms.get(pos));
                context.startActivity(intent);
            }
        });
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rowName.setText(rooms.get(position).getName());
        holder.rowarea.setText("Area : "+rooms.get(position).getArea() );
        holder.rowid.setText(""+rooms.get(position).getId());
        holder.rowprice.setText("Price :"+rooms.get(position).getPrice()+"$");
        holder.rowImage.setImageResource(images[position]);

        if(rooms.get(position).getAvailable()==0) {
            holder.ava.setText("Available");
        }else{
            holder.ava.setText("Reserved");
        }
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}
