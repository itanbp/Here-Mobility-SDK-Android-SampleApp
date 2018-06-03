package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nil.test.sdk.sampleapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itanbp on 03/06/2018.
 */
public class ConcertsAdapter extends RecyclerView.Adapter<ConcertsAdapter.ViewHolder> {


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView objectTitle;
        ImageView objectImage;
        ImageView bookmarkButton;
        ImageView editButton;

        ViewHolder(View view) {
            super(view);

            /*
            objectTitle = view.findViewById(R.id.virtual_object_title);
            objectImage = view.findViewById(R.id.virtual_object_image);
            bookmarkButton = view.findViewById(R.id.virtual_object_bookmark);
            editButton = view.findViewById(R.id.virtual_object_edit);

            objectImage.setOnClickListener(listener);
            bookmarkButton.setOnClickListener(listener);
            editButton.setOnClickListener(listener);
            */
        }
    }


    private ArrayList<Concert> concertsList;



    public ConcertsAdapter() {
        concertsList = getMockData();
    }


    @NonNull
    @Override
    public ConcertsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.concerts_gallery_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcertsAdapter.ViewHolder holder, int position) {

        Concert concert = concertsList.get(position);

         /*
        Context context = holder.objectImage.getContext();


        holder.objectImage.setImageDrawable(context.getDrawable(virtualObject.image));
        holder.objectTitle.setText(virtualObject.name);
        */

    }

    @Override
    public int getItemCount() {
        return concertsList.size();
    }


    private static View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /*
            Snackbar snackbar = Snackbar.make(view, R.string.not_developed, Snackbar.LENGTH_LONG);
            snackbar.show();
            */
        }
    };




    private ArrayList<Concert> getMockData() {
        ArrayList<Concert> concerts = new ArrayList<>();
        Concert concert = new Concert("Beyonce &amp; Jay.Z", "London Stadium, London", "June 15-16, 2018  |  18:00", R.drawable.pic_beynonce);
        concerts.add(concert);
        return concerts;
    }


}
