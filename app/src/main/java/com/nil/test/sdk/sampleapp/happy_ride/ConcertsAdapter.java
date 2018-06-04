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


    interface ConcertListener {
        void concertChoose(Concert concert);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView arena;
        TextView date;
        View view;

        ViewHolder(View view) {
            super(view);

            image = view.findViewById(R.id.gallery_card_image);
            title = view.findViewById(R.id.gallery_card_title);
            arena = view.findViewById(R.id.gallery_card_arena);
            date = view.findViewById(R.id.gallery_card_date);
            this.view = view;

        }
    }


    private ConcertListener listener;
    private ArrayList<Concert> concertsList;



    public ConcertsAdapter(ConcertListener listener) {
        concertsList = getMockData();
        this.listener = listener;
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

        Context context = holder.image.getContext();

        holder.image.setImageDrawable(context.getDrawable(concert.drawableId));
        holder.title.setText(concert.title);
        holder.arena.setText(concert.arena);
        holder.date.setText(concert.date);

        holder.view.setOnClickListener(v -> {
                if (listener != null) {
                    listener.concertChoose(concert);
                }
        });
    }

    @Override
    public int getItemCount() {
        return concertsList.size();
    }



    private ArrayList<Concert> getMockData() {

        ArrayList<Concert> concerts = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();

        dates.add("18:00, June 15, 2018 @ London Stadium");
        dates.add("18:00, June 16, 2018 @ London Stadium");
        Concert concert = new Concert("Beyonce & Jay.Z", "London Stadium, London", "June 15-16, 2018  |  18:00", R.drawable.pic_beynonce, dates);
        concerts.add(concert);

        dates = new ArrayList<>();
        dates.add("18:00, June 14, 2018 @ London Stadium");
        dates.add("18:00, June 17, 2018 @ London Stadium");
        concert = new Concert("Ed Sheeran", "London Stadium, London", "June 14-17, 2018  |  18:00", R.drawable.pic_ed, dates);
        concerts.add(concert);

        dates = new ArrayList<>();
        dates.add("17:00, June 18, 2018 @ O2 Arena");
        dates.add("17:00, June 20, 2018 @ O2 Arena");
        concert = new Concert("Kanye West", "O2 Arena, London", "June 18-20, 2018 | 17:00", R.drawable.pic_kanye, dates);
        concerts.add(concert);

        return concerts;
    }


}
